package com.ta.smile.ui.analysis

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ta.smile.R
import com.ta.smile.databinding.FragmentFecesResultBinding
import com.ta.smile.ui.MainViewModel
import com.ta.smile.ui.ViewModelFactory

class FecesResultFragment: Fragment() {

    private var _binding: FragmentFecesResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var pieFecesColorDataSet: PieDataSet
    private lateinit var fecesColorChart: PieChart
    private lateinit var mainViewModel: MainViewModel

    //Color feces class
    private val colorClass: MutableList<Int> = mutableListOf(
        Color.parseColor("#64492A"),
        Color.parseColor("#E74646"),
        Color.parseColor("#2C3333"),
        Color.parseColor("#EFD1AA")
    )

    private fun dataFesCol(normal: Float, red: Float, black: Float, pale: Float ): ArrayList<PieEntry>?{
        val data: ArrayList<PieEntry> = ArrayList()
        data.add(PieEntry(normal, "Normal"))
        data.add(PieEntry(red, "Kemerahan"))
        data.add(PieEntry(black, "Kehitaman"))
        data.add(PieEntry(pale, "Pucat"))
        return data
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFecesResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
        mainViewModel.getFecesColorDataNL()
        mainViewModel.getFecesFormDataNL()
        mainViewModel.getLatestFecesColor().observe(viewLifecycleOwner){
            if (it.createdAt == "-"){
                binding.tvNoData.visibility = View.VISIBLE
                binding.tvDominantColorFeces.visibility = View.GONE
                binding.tvFecesColorDesc.visibility = View.GONE
                binding.ivDominantColor.visibility = View.GONE
                binding.tvFecesColor.visibility = View.GONE
            } else {
                binding.tvNoData.visibility = View.GONE
                binding.tvDominantColorFeces.visibility = View.VISIBLE
                binding.tvFecesColorDesc.visibility = View.VISIBLE
                binding.ivDominantColor.visibility = View.VISIBLE
                binding.tvFecesColor.visibility = View.VISIBLE
                showFecesColor(
                    listOf(it.normal, it.kemerahan, it.kehitaman, it.pucat) as List<Float>,
                    it.type
                )
            }
        }
        mainViewModel.getLatestFecesForm().observe(viewLifecycleOwner){
            if (it.createdAt == "-"){
                binding.tvNoData.visibility = View.VISIBLE
                binding.tvFecesFormDesc.visibility = View.GONE
                binding.tvFecesFormType.visibility = View.GONE
                binding.ivFecesFormType.visibility = View.GONE
                binding.tvFecesForm.visibility = View.GONE
                binding.ivFecesForm.visibility = View.GONE
            } else {
                binding.tvNoData.visibility = View.GONE
                binding.tvFecesFormDesc.visibility = View.VISIBLE
                binding.tvFecesFormType.visibility = View.VISIBLE
                binding.ivFecesFormType.visibility = View.VISIBLE
                binding.tvFecesForm.visibility = View.VISIBLE
                binding.ivFecesForm.visibility = View.VISIBLE
                showFecesForm(it.type)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getLatestFecesColor().observe(viewLifecycleOwner){
            showFecesColor(
                listOf(it.normal, it.kemerahan, it.kehitaman, it.pucat) as List<Float>,
                it.type
            )
        }
        mainViewModel.getLatestFecesForm().observe(viewLifecycleOwner){
            showFecesForm(it.type)
        }
    }

    private fun initView() {
        /*Feces color chart*/
        fecesColorChart = requireView().findViewById(R.id.pc_result_feces)
        pieFecesColorDataSet = PieDataSet(dataFesCol(98f,19f,21f,15f),"Feces Color")
        binding.tvPercentageFecesColor.visibility = View.GONE
        pieFecesColorDataSet.colors = colorClass
        pieFecesColorDataSet.sliceSpace = 2f
        pieFecesColorDataSet.valueTextSize = 8f
        pieFecesColorDataSet.setDrawValues(false)
//        pieColorDataSet.setDrawIcons(true)
//        val font = Typeface.createFromAsset(context?.assets, "font/quicksand_light.ttf")
//        val typeface = context?.let { ResourcesCompat.getFont(it, R.font.karla_light) };
//        pieColorDataSet.valueTypeface = font
        val pieDataColorFeces = PieData(pieFecesColorDataSet)
        fecesColorChart.data = pieDataColorFeces
        fecesColorChart.setDrawEntryLabels(false)
        fecesColorChart.description?.isEnabled = false
        fecesColorChart.setUsePercentValues(true)
        fecesColorChart.setDrawMarkers(false)
        fecesColorChart.holeRadius = 65f
        fecesColorChart.transparentCircleRadius = 75f
        fecesColorChart.isRotationEnabled = false
        fecesColorChart.setDrawEntryLabels(false)
        fecesColorChart.legend?.isEnabled = false
//        val h: Highlight = Highlight(0f, 0f, 0) // dataset index for piechart is always 0
//        fecesColorChart?.highlightValues(arrayOf(h))
        fecesColorChart.invalidate()
        //animation
        fecesColorChart.animateY(1500, Easing.EaseInOutQuad)
        /*End of Chart*/
    }

    private fun showFecesForm(type: Int?){
        binding.ivFecesForm.setImageResource(
            when(type){
                0 -> R.drawable.tipe_1
                1 -> R.drawable.tipe_2
                2 -> R.drawable.tipe_3
                3 -> R.drawable.tipe_4
                4 -> R.drawable.tipe_5
                5 -> R.drawable.tipe_6
                else -> R.drawable.tipe_7
            }
        )
        binding.tvFecesFormDesc.text = when(type){
            0 -> resources.getString(R.string.desc_feces_type_1)
            1 -> resources.getString(R.string.desc_feces_type_2)
            2 -> resources.getString(R.string.desc_feces_type_3)
            3 -> resources.getString(R.string.desc_feces_type_4)
            4 -> resources.getString(R.string.desc_feces_type_5)
            5 -> resources.getString(R.string.desc_feces_type_6)
            else -> resources.getString(R.string.desc_feces_type_7)
        }
        binding.tvFecesFormType.text = when(type){
            0 -> "Tipe 1"
            1 -> "Tipe 2"
            2 -> "Tipe 3"
            3 -> "Tipe 4"
            4 -> "Tipe 5"
            5 -> "Tipe 6"
            else -> "Tipe 7"
        }
    }

    private fun showFecesColor(fecColData: List<Float>, type: Int?){
        Log.d("TAGfeccol", "showFecesColor: $fecColData")
        /*Feces color chart*/
        pieFecesColorDataSet = PieDataSet(dataFesCol(fecColData[0],fecColData[1],fecColData[2],fecColData[3],),"Feces Color")
        pieFecesColorDataSet.colors = colorClass
        pieFecesColorDataSet.sliceSpace = 2f
        pieFecesColorDataSet.valueTextSize = 8f
        pieFecesColorDataSet.setDrawValues(false)
//        pieColorDataSet.setDrawIcons(true)
//        val font = Typeface.createFromAsset(context?.assets, "font/quicksand_light.ttf")
//        val typeface = context?.let { ResourcesCompat.getFont(it, R.font.karla_light) };
//        pieColorDataSet.valueTypeface = font
        val pieDataColorFeces = PieData(pieFecesColorDataSet)
        fecesColorChart.data = pieDataColorFeces
        fecesColorChart.setDrawEntryLabels(false)
        fecesColorChart.description?.isEnabled = false
        fecesColorChart.setUsePercentValues(true)
        fecesColorChart.setDrawMarkers(false)
        fecesColorChart.holeRadius = 65f
        fecesColorChart.transparentCircleRadius = 75f
        fecesColorChart.isRotationEnabled = false
        fecesColorChart.setDrawEntryLabels(false)
        fecesColorChart.legend?.isEnabled = false
//        val h: Highlight = Highlight(0f, 0f, 0) // dataset index for piechart is always 0
//        fecesColorChart?.highlightValues(arrayOf(h))
        fecesColorChart.invalidate()
        //animation
        fecesColorChart.animateY(1500, Easing.EaseInOutQuad)
        /*End of Chart*/

        /*View Integration*/
        binding.ivDominantColor.setImageResource(when(type){
            0 -> R.color.black_feces
            1 -> R.color.red_feces
            2 -> R.color.brown_feces
            else -> R.color.pale_feces
        })
        binding.tvDominantColorFeces.text = when(type){
            0 -> "Kehitaman"
            1 -> "Kemerahan"
            2 -> "Normal/Coklat"
            else -> "Pucat"
        }
//        binding.tvPercentageFecesColor.text = when(type){
//            0 -> "${(fecColData[0]*100).toInt()}" + "%"
//            1 -> "${(fecColData[1]*100).toInt()}" + "%"
//            2 -> "${(fecColData[2]*100).toInt()}" + "%"
//            else -> "${(fecColData[3]*100).toInt()}" + "%"
//        }
        binding.tvFecesColorDesc.text = when(type){
            0 -> resources.getString(R.string.desc_feces_kehitaman)
            1 -> resources.getString(R.string.desc_feces_kemerahan)
//            2 -> resources.getString(R.string.desc_feces_normal)
            2 -> "Selamat! Feses anda berwarna kuning kecoklatan yang menandakan feses yang sehat. Namun, jika feses Anda berwarna kuning kecoklatan dan telihat berminyak serta berbau busuk dapat disebabkan karena gangguan pencernaan seperti penyakit celiac."
            else -> resources.getString(R.string.desc_feces_pucat)
        }
//        binding.tvPercentageFecesColor.visibility = View.VISIBLE
//        binding.tvFecesColorDesc.text = when(type){
//            0 -> resources.getString(R.string.desc_feces_brown)
//            1 -> "${(fecColData[1]*100).toInt()}" + "%"
//            2 -> resources.getString(R.string.desc_feces_black)
//            else -> "${(fecColData[3]*100).toInt()}" + "%"
//        }
    }

    private fun initAction() {
        mainViewModel = obtainMainViewModel(requireActivity() as AppCompatActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun obtainMainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

}