package com.ta.smile.ui.analysis


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.smarttoilet.adapter.FecesSectionPagerAdapter
import com.project.smarttoilet.adapter.UrineSectionPagerAdapter
import com.ta.smile.R
import com.ta.smile.database.feces.color.FecesColorData
import com.ta.smile.database.feces.form.FecesFormData
import com.ta.smile.database.urine.UrineColorData
import com.ta.smile.databinding.FragmentAnalysisBinding
import com.ta.smile.ui.MainViewModel
import com.ta.smile.ui.ViewModelFactory
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class AnalysisFragment : Fragment() {

    private var _binding: FragmentAnalysisBinding? = null
    private var mViewPager: ViewPager? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val analysisViewModel =
            ViewModelProvider(this).get(AnalysisViewModel::class.java)
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        val uid = firebaseUser?.uid
        Log.d(TAG, "display name: ${firebaseUser?.displayName}")
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        initView()
        mainViewModel = obtainMainViewModel(requireActivity() as AppCompatActivity)
        mainViewModel.getFecesColorData().observe(viewLifecycleOwner){
            if(it.size > 1){
                mainViewModel.deleteInitialFc("-")
                mainViewModel.deleteInitialFf("-")
            }
        }
        mainViewModel.getUrineColorData().observe(viewLifecycleOwner){
            if(it.size > 1){
                mainViewModel.deleteInitialUc("-")
            }
        }

        mainViewModel.getFecesColorDataNL()
        mainViewModel.getLatestFecesColor().observe(viewLifecycleOwner){ date ->
            if (date.createdAt == "-"){
                binding.tvDateUpdated.text = "Data terakhir: -"
            } else {
                val inst: OffsetDateTime = OffsetDateTime.ofInstant(
                    Instant.parse(date.createdAt),
                    ZoneId.systemDefault()
                )
                val dataTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(inst)
//                Log.d(TAG, "onViewCreated123: $dataTime")
                binding.tvDateUpdated.text = "Data terakhir: $dataTime"
            }
        }
        mainViewModel.getUrineColorDataNL()
        mainViewModel.getLatestUrineColor().observe(viewLifecycleOwner){ date ->
            if (date.createdAt == "-"){
                binding.tvDateUpdated2.text = "Data terakhir: -"
            } else {
                val inst: OffsetDateTime = OffsetDateTime.ofInstant(
                    Instant.parse(date.createdAt),
                    ZoneId.systemDefault()
                )
                val dataTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(inst)
//                Log.d(TAG, "onViewCreated123: $dataTime")
                binding.tvDateUpdated2.text = "Data terakhir: $dataTime"
                mainViewModel.getLatestUrineColor().removeObservers(viewLifecycleOwner)
            }
        }
        binding.refreshLayoutAnalysis.setOnRefreshListener {
            Log.d(TAG, "onViewCreated: $uid")
            mainViewModel.getData2(uid)
//            mainViewModel.getData2("T8RJD6EPcubXnVwq9UzxrY5mNCy2")
//            Log.d(TAG, "onViewCreated: called again1")
            mainViewModel.result.observe(viewLifecycleOwner){ serverData ->
                Log.d(TAG, "Data dari server: $serverData")
                if (serverData.type == 0){
                    // Feces color data handling
                    val fecesColorResult = serverData.result.subList(7,11)
                    val colorClasses = floatArrayOf(
                        fecesColorResult[0].toFloat(),
                        fecesColorResult[1].toFloat(),
                        fecesColorResult[2].toFloat(),
                        fecesColorResult[3].toFloat()
                    )
                    val classColorType = colorClasses.indices.maxByOrNull { colorClasses[it] } ?: -1
                    val dataFw = FecesColorData(
                        kehitaman = fecesColorResult[0].toFloat(),
                        kemerahan = fecesColorResult[1].toFloat(),
                        normal = fecesColorResult[2].toFloat(),
                        pucat = fecesColorResult[3].toFloat(),
                        type = classColorType,
                        createdAt = serverData.createdAt
                    )
                    mainViewModel.insertFecesColor(dataFw)
                    mainViewModel.getFecesColorDataNL()


                    // Feces form data handling
                    val fecesFormResult = serverData.result.subList(0, 7)
                    val formClasses = floatArrayOf(
                        fecesFormResult[0].toFloat(),
                        fecesFormResult[1].toFloat(),
                        fecesFormResult[2].toFloat(),
                        fecesFormResult[3].toFloat(),
                        fecesFormResult[4].toFloat(),
                        fecesFormResult[5].toFloat(),
                        fecesFormResult[6].toFloat()
                    )

                    val classFormType = formClasses.indices.maxByOrNull { formClasses[it] } ?: -1
                    val dataFb = FecesFormData(
                        tipe1 = fecesFormResult[0].toFloat(),
                        tipe2 = fecesFormResult[1].toFloat(),
                        tipe3 = fecesFormResult[2].toFloat(),
                        tipe4 = fecesFormResult[3].toFloat(),
                        tipe5 = fecesFormResult[4].toFloat(),
                        tipe6 = fecesFormResult[5].toFloat(),
                        tipe7 = fecesFormResult[6].toFloat(),
                        type = classFormType,
                        createdAt = serverData.createdAt
                    )
                    mainViewModel.insertFecesForm(dataFb)
                    mainViewModel.getFecesFormDataNL()

                }
                else {
                    //Urine color data handling
                    val urineClasses = floatArrayOf(
                        serverData.result[0].toFloat(),
                        serverData.result[1].toFloat(),
                        serverData.result[2].toFloat(),
                        serverData.result[3].toFloat(),

                    )
                    val classUrineType = urineClasses.indices.maxByOrNull { urineClasses[it] } ?: -1
                    val dataUw =  UrineColorData(
                        coklat = serverData.result[0].toFloat(),
                        kuning_g = serverData.result[1].toFloat(),
                        merah = serverData.result[2].toFloat(),
                        transparan = serverData.result[3].toFloat(),
                        type = classUrineType,
                        createdAt = serverData.createdAt
                    )
                    mainViewModel.insertUrineColor(dataUw)
                    mainViewModel.getUrineColorDataNL()

                }
            }
            mainViewModel.isLoading.observe(viewLifecycleOwner) {
                if (!it) {
                    binding.refreshLayoutAnalysis.isRefreshing = false
                }
            }
        }
    }

    private fun initView(){
        //TabLayout Mediator for feces
        val fecesSectionPagerAdapter = FecesSectionPagerAdapter(requireActivity() as AppCompatActivity)
        val fecesViewPager: ViewPager2? = view?.findViewById(R.id.view_pager_feces)
        fecesViewPager?.adapter = fecesSectionPagerAdapter
        val tabsFeces: TabLayout? = view?.findViewById(R.id.tab_layout_feces)
        if (tabsFeces != null) {
            if (fecesViewPager != null) {
                TabLayoutMediator(tabsFeces, fecesViewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }

        //TabLayout Mediator for urine
        val urineSectionPagerAdapter = UrineSectionPagerAdapter(requireActivity() as AppCompatActivity)
        val urineViewPager: ViewPager2? = view?.findViewById(R.id.view_pager_urine)
        urineViewPager?.adapter = urineSectionPagerAdapter
        val tabsUrine: TabLayout? = view?.findViewById(R.id.tab_layout_urine)
        if (tabsUrine != null) {
            if (urineViewPager != null) {
                TabLayoutMediator(tabsUrine, urineViewPager){ tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }
    }

    private fun obtainMainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            com.ta.smile.R.string.tab_text_feces_result,
            com.ta.smile.R.string.tab_text_feces_tips
        )

        private const val TAG = "Analysis Fragment"
    }
}