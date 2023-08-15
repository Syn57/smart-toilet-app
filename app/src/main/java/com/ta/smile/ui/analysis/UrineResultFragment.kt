package com.ta.smile.ui.analysis

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.ta.smile.R
import com.ta.smile.databinding.FragmentFecesResultBinding
import com.ta.smile.databinding.FragmentUrineResultBinding
import com.ta.smile.ui.MainViewModel
import com.ta.smile.ui.ViewModelFactory

class UrineResultFragment: Fragment() {

    private var _binding: FragmentUrineResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var pieUrineColorDataSet: PieDataSet
    private lateinit var urineColorChart: PieChart
    private lateinit var mainViewModel: MainViewModel

    //Color feces class
    private val colorClass: MutableList<Int> = mutableListOf(
        Color.parseColor("#E07800"),
        Color.parseColor("#D3B100"),
//        Color.parseColor("#A9A16B"),
//        Color.parseColor("#BEB645"),
        Color.parseColor("#F70003"),
//        Color.parseColor("#E55500"),
//        Color.parseColor("#C394B0"),
        Color.parseColor("#AFAEAC"),
//        Color.parseColor("#E29000")

    )

    private fun dataUrCol(coklat: Float, kuning_gelap: Float, merah: Float,  transparan: Float): ArrayList<PieEntry>?{
        val data: ArrayList<PieEntry> = ArrayList()
        data.add(PieEntry(coklat, "Coklat"))
        data.add(PieEntry(kuning_gelap, "Kuning Gelap"))
//        data.add(PieEntry(kuning_sp, "Kuning Sangat Pucat"))
//        data.add(PieEntry(kuning_t, "Kuning Transparan"))
        data.add(PieEntry(merah, "Merah"))
//        data.add(PieEntry(orange, "Orange"))
//        data.add(PieEntry(pink, "Pink"))
        data.add(PieEntry(transparan, "Transparan"))
//        data.add(PieEntry(madu, "Madu"))
        return data
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUrineResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getLatestUrineColor().observe(viewLifecycleOwner){
            if(it.createdAt == "-"){
                binding.tvNoData.visibility = View.VISIBLE
                binding.tvDominantColorUrine.visibility = View.GONE
                binding.tvUrineColorDesc.visibility = View.GONE
                binding.ivDominantColor.visibility = View.GONE
                binding.tvUrineColor.visibility = View.GONE
            } else {
                binding.tvNoData.visibility = View.GONE
                binding.tvDominantColorUrine.visibility = View.VISIBLE
                binding.tvUrineColorDesc.visibility = View.VISIBLE
                binding.ivDominantColor.visibility = View.VISIBLE
                binding.tvUrineColor.visibility = View.VISIBLE
                showUrCol(listOf(it.coklat, it.kuning_g, it.merah, it.transparan) as List<Float>, it.type)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
    }

    private fun initView() {
        /*Feces color chart*/
        urineColorChart = requireView().findViewById(R.id.pc_result_urine)
        pieUrineColorDataSet = PieDataSet(dataUrCol(50f,50f,50f,50f),"Urine Color")
        pieUrineColorDataSet.colors = colorClass
        pieUrineColorDataSet.sliceSpace = 2f
        pieUrineColorDataSet.valueTextSize = 8f
        pieUrineColorDataSet.setDrawValues(false)
        //        pieColorDataSet.setDrawIcons(true)
//        val font = Typeface.createFromAsset(context?.assets, "font/quicksand_light.ttf")
//        val typeface = context?.let { ResourcesCompat.getFont(it, R.font.karla_light) };
//        pieColorDataSet.valueTypeface = font
        val pieDataColorFeces = PieData(pieUrineColorDataSet)
        urineColorChart.data = pieDataColorFeces
        urineColorChart.setDrawEntryLabels(false)
        urineColorChart.description?.isEnabled = false
        urineColorChart.setUsePercentValues(true)
        urineColorChart.setDrawMarkers(false)
        urineColorChart.holeRadius = 65f
        urineColorChart.transparentCircleRadius = 75f
        urineColorChart.isRotationEnabled = false
        urineColorChart.setDrawEntryLabels(false)
        urineColorChart.legend?.isEnabled = false
//        val h: Highlight = Highlight(0f, 0f, 0) // dataset index for piechart is always 0
//        urineColorChart?.highlightValues(arrayOf(h))
        urineColorChart.invalidate()
        //animation
        urineColorChart.animateY(1500, Easing.EaseInOutQuad)
        /*End of Chart*/
    }

    private fun showUrCol(urColData: List<Float>, type: Int?){
        /*Feces color chart*/
        urineColorChart = requireView().findViewById(R.id.pc_result_urine)
        pieUrineColorDataSet = PieDataSet(dataUrCol(urColData[0],urColData[1],urColData[2],urColData[3]),"Urine Color")
        pieUrineColorDataSet.colors = colorClass
        pieUrineColorDataSet.sliceSpace = 2f
        pieUrineColorDataSet.valueTextSize = 8f
        pieUrineColorDataSet.setDrawValues(false)
        //        pieColorDataSet.setDrawIcons(true)
//        val font = Typeface.createFromAsset(context?.assets, "font/quicksand_light.ttf")
//        val typeface = context?.let { ResourcesCompat.getFont(it, R.font.karla_light) };
//        pieColorDataSet.valueTypeface = font
        val pieDataColorFeces = PieData(pieUrineColorDataSet)
        urineColorChart.data = pieDataColorFeces
        urineColorChart.setDrawEntryLabels(false)
        urineColorChart.description?.isEnabled = false
        urineColorChart.setUsePercentValues(true)
        urineColorChart.setDrawMarkers(false)
        urineColorChart.holeRadius = 65f
        urineColorChart.transparentCircleRadius = 75f
        urineColorChart.isRotationEnabled = false
        urineColorChart.setDrawEntryLabels(false)
        urineColorChart.legend?.isEnabled = false
//        val h: Highlight = Highlight(0f, 0f, 0) // dataset index for piechart is always 0
//        urineColorChart?.highlightValues(arrayOf(h))
        urineColorChart.invalidate()
        //animation
        urineColorChart.animateY(1500, Easing.EaseInOutQuad)
        /*End of Chart*/

        /*View Integration*/
        binding.ivDominantColor.setImageResource(when(type){
            0-> R.color.coklat_urine
            1-> R.color.kuning_t_urine
            2-> R.color.merah_urine
            else -> R.color.transparan_urine
        })

        binding.tvDominantColorUrine.text = when(type){
            0-> "Coklat"
            1-> "Kuning gelap"
            2-> "Merah"
            else -> "Transparan"
        }

        binding.tvUrineColorDesc.text = when(type){
            0-> resources.getString(R.string.desc_urine_brown)
            1-> resources.getString(R.string.desc_urine_yellow)
            2-> resources.getString(R.string.desc_urine_red)
            else -> resources.getString(R.string.desc_urine_transparent)
        }
//        binding.tvPercentageUrineColor.text = when(type){
//            0-> "${(urColData[0]*100).toInt()}" + "%"
//            1-> "${(urColData[1]*100).toInt()}" + "%"
//            2-> "${(urColData[2]*100).toInt()}" + "%"
//            3-> "${(urColData[3]*100).toInt()}" + "%"
//            4-> "${(urColData[4]*100).toInt()}" + "%"
//            5-> "${(urColData[5]*100).toInt()}" + "%"
//            6-> "${(urColData[6]*100).toInt()}" + "%"
//            7-> "${(urColData[7]*100).toInt()}" + "%"
//            else -> "${(urColData[8]*100).toInt()}" + "%"
//        }
//        binding.tvPercentageUrineColor.visibility = View.VISIBLE
//        binding.tvUrineColorDesc.text = when(type){
//            0-> "Coklat"
//            1-> "Kuning gelap"
//            2-> "Kuning sangat pucat"
//            3-> "Kuning transparan"
//            4-> "Merah"
//            5-> "Orange"
//            6-> "Pink"
//            7-> "Transparan"
//            else -> "Madu"
//        }
    }

    private fun initAction() {
        mainViewModel = obtainMainViewModel(requireActivity() as AppCompatActivity)
    }

    private fun obtainMainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}