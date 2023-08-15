package com.ta.smile.ui.history

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.ta.smile.R
import com.ta.smile.ui.MainViewModel
import com.ta.smile.ui.ViewModelFactory
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class UrineHistoryFragment: Fragment() {

    private lateinit var mainViewModel: MainViewModel

    class MyXAxisFormatterUrineColor : ValueFormatter() {
        private val days = arrayOf("Coklat", "Kuning", "Merah", "Transparan")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt()) ?: value.toString()
        }
    }

    override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    return inflater.inflate(R.layout.fragment_urine_history, container, false)
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = obtainMainViewModel(requireActivity() as AppCompatActivity)
        mainViewModel.getLatestUrineColor().observe(viewLifecycleOwner){
            if(it.createdAt == "-"){
                val urineColorHistoryChart = getView()?.findViewById<LineChart>(R.id.sc_history_urine_color)
                urineColorHistoryChart?.visibility = View.GONE
                val tvNoDataUc = getView()?.findViewById<TextView>(R.id.tv_no_data_uc)
                tvNoDataUc?.visibility = View.VISIBLE
            } else {
                val urineColorHistoryChart = getView()?.findViewById<LineChart>(R.id.sc_history_urine_color)
                urineColorHistoryChart?.visibility = View.VISIBLE
                val tvNoDataUc = getView()?.findViewById<TextView>(R.id.tv_no_data_uc)
                tvNoDataUc?.visibility = View.GONE
                showUrineColorHistory()
            }
        }
    }

    private fun showUrineColorHistory() {
        mainViewModel.getUrineColorData().observe(viewLifecycleOwner) {
            val dataUc: ArrayList<Entry> = ArrayList()
            var i = 1f
            for (data in (0..it.size-1).reversed()){
                dataUc.add(Entry (i, (it[data].type?.toFloat() ?: 0) as Float))
                i++
            }
            /* Urine Color chart */
            val urineColorHistoryChart = getView()?.findViewById<LineChart>(R.id.sc_history_urine_color)
            val lineUrineColorDataset = LineDataSet(dataUc, "ok")
            lineUrineColorDataset.setDrawFilled(true)
            lineUrineColorDataset.color = Color.parseColor("#F2DF3A")
            lineUrineColorDataset.setCircleColor(Color.parseColor("#F2DF3A"))
            lineUrineColorDataset.fillDrawable =
                ContextCompat.getDrawable(requireActivity(), R.drawable.gradient_urine)
            val iLineDatasetUrineColor = ArrayList<ILineDataSet>()
            iLineDatasetUrineColor.add(lineUrineColorDataset)
            val lineUrineColorData = LineData(iLineDatasetUrineColor)
            urineColorHistoryChart?.data = lineUrineColorData
//        fecesColorHistoryChart?.setVisibleXRangeMaximum(20f)
            urineColorHistoryChart?.setMaxVisibleValueCount(1)
            urineColorHistoryChart?.setVisibleXRange(0f, 21f)
            urineColorHistoryChart?.description?.isEnabled = false
            urineColorHistoryChart?.setDrawGridBackground(false)
//        fecesColorHistoryChart?.xAxis?.isEnabled = false
//        fecesColorHistoryChart?.axisLeft?.isEnabled = false
            urineColorHistoryChart?.axisRight?.isEnabled = false
//        fecesColorHistoryChart?.setVisibleYRange(0f,6f, YAxis.AxisDependency.RIGHT)
            urineColorHistoryChart?.isDragXEnabled = false
            urineColorHistoryChart?.isScaleXEnabled = false
//        fecesColorHistoryChart?.setDrawEntryLabels(false)
            urineColorHistoryChart?.legend?.isEnabled = false
            // Controlling X axis
            val xAxis = urineColorHistoryChart?.xAxis
            // Set the xAxis position to bottom. Default is top
            xAxis?.position = XAxis.XAxisPosition.BOTTOM
            //Customizing left axis value
            val leftAxis = urineColorHistoryChart?.axisLeft
            leftAxis?.granularity = 1f // minimum axis-step (interval) is 1
            leftAxis?.valueFormatter = MyXAxisFormatterUrineColor()
            urineColorHistoryChart?.invalidate()
//        fecesColorHistoryChart?.animateY(1500,Easing.EaseInOutQuart)
            urineColorHistoryChart?.animateX(3500, Easing.EaseInOutQuad)
            /* End feces color chart */

            //Date integration
            val tvDateOldest = view?.findViewById<TextView>(R.id.tv_oldest_urine_data)
            val tvDateNewest = view?.findViewById<TextView>(R.id.tv_newest_urine_data)

            if (it[it.size - 1].createdAt != "-") {
                val inst: OffsetDateTime = OffsetDateTime.ofInstant(
                    Instant.parse(it[it.size - 1].createdAt),
                    ZoneId.systemDefault()
                )
                val dataTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(inst)
                tvDateNewest?.text = "Data terbaru: $dataTime"
                Log.d("time1", "onViewCreated: $dataTime")
            } else {
                tvDateNewest?.text = "Data terbaru: -"
                Log.d("time1", "onViewCreated: ${it[it.size - 1].createdAt}")
            }

            if (it[0].createdAt != "-"){
                val inst2: OffsetDateTime = OffsetDateTime.ofInstant(
                    Instant.parse(it[0].createdAt),
                    ZoneId.systemDefault()
                )
                val dataTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(inst2)
//                Log.d("time2", "onViewCreated: $dataTime")
                tvDateOldest?.text = "Data terlama: $dataTime"
            } else {
                tvDateOldest?.text = "Data terlama: -"
//                Log.d("time2", "onViewCreated: ${it[0].createdAt}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun obtainMainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }


}