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

class FecesHistoryFragment: Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feces_history, container, false)
    }

    class MyXAxisFormatterColor : ValueFormatter() {
        private val days = arrayOf("","Kehitaman", "Kemerahan", "Normal", "Pucat")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt()) ?: value.toString()
        }
    }

    class MyXAxisFormatterForm : ValueFormatter() {
        private val days = arrayOf("","Tipe 1", "Tipe 2", "Tipe 3", "Tipe 4", "Tipe 5", "Tipe 6","Tipe 7")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt()) ?: value.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = obtainMainViewModel(requireActivity() as AppCompatActivity)

        mainViewModel.getLatestFecesColor().observe(viewLifecycleOwner){
            if (it.createdAt == "-"){
                val fecesColorHistoryChart = getView()?.findViewById<LineChart>(R.id.sc_history_feces_color)
                fecesColorHistoryChart?.visibility = View.GONE
                val tvNoDataFc = getView()?.findViewById<TextView>(R.id.tv_no_data_fc)
                tvNoDataFc?.visibility = View.VISIBLE
            } else {
                val fecesColorHistoryChart = getView()?.findViewById<LineChart>(R.id.sc_history_feces_color)
                fecesColorHistoryChart?.visibility = View.VISIBLE
                val tvNoDataFc = getView()?.findViewById<TextView>(R.id.tv_no_data_fc)
                tvNoDataFc?.visibility = View.GONE
                showFecesColorHistory()
            }
        }
        mainViewModel.getLatestFecesForm().observe(viewLifecycleOwner){
            if(it.createdAt == "-"){
                val fecesFormHistoryChart = getView()?.findViewById<LineChart>(R.id.sc_history_feces_form)
                fecesFormHistoryChart?.visibility = View.GONE
                val tvNoDataFf = getView()?.findViewById<TextView>(R.id.tv_no_data_ff)
                tvNoDataFf?.visibility = View.VISIBLE
            } else {
                val fecesFormHistoryChart = getView()?.findViewById<LineChart>(R.id.sc_history_feces_form)
                fecesFormHistoryChart?.visibility = View.VISIBLE
                val tvNoDataFf = getView()?.findViewById<TextView>(R.id.tv_no_data_ff)
                tvNoDataFf?.visibility = View.GONE
                showFecesFormHistory()
            }
        }
    }

    private fun showFecesFormHistory() {
        mainViewModel.getFecesFormData().observe(viewLifecycleOwner){
            val dataFb: ArrayList<Entry> = ArrayList()
            var i = 1f
            for (data in (0..it.size-1).reversed()){
                dataFb.add(Entry(i, (it[data].type?.toFloat()?.plus(1)  ?: 0) as Float))
                Log.d("TAG", "onViewCreated1: $data")
                i++
            }

            /* Feces form history chart*/
            val fecesFormHistoryChart = getView()?.findViewById<LineChart>(R.id.sc_history_feces_form)
            val lineFecesFormDataset = LineDataSet(dataFb,"ok")
            lineFecesFormDataset.color = Color.parseColor("#76BA99")
            lineFecesFormDataset.setDrawFilled(true)
            lineFecesFormDataset.setCircleColor(Color.parseColor("#76BA99"))
            lineFecesFormDataset.fillDrawable = ContextCompat.getDrawable(requireActivity(), R.drawable.gradient_feces_form)
            val iLineDatasetFecesForm = ArrayList<ILineDataSet>()
            iLineDatasetFecesForm.add(lineFecesFormDataset)
            val lineFecesFormData = LineData(iLineDatasetFecesForm)
            fecesFormHistoryChart?.data = lineFecesFormData
//        fecesColorHistoryChart?.setVisibleXRangeMaximum(20f)
            fecesFormHistoryChart?.setMaxVisibleValueCount(1)
            fecesFormHistoryChart?.setVisibleXRange(0f,13f)
            fecesFormHistoryChart?.description?.isEnabled = false
            fecesFormHistoryChart?.setDrawGridBackground(false)
//        fecesColorHistoryChart?.xAxis?.isEnabled = false
//        fecesColorHistoryChart?.axisLeft?.isEnabled = false
            fecesFormHistoryChart?.axisRight?.isEnabled = false
//        fecesColorHistoryChart?.setVisibleYRange(0f,6f, YAxis.AxisDependency.RIGHT)
            fecesFormHistoryChart?.isDragXEnabled = false
            fecesFormHistoryChart?.isScaleXEnabled = false
//        fecesColorHistoryChart?.setDrawEntryLabels(false)
            fecesFormHistoryChart?.legend?.isEnabled = false
            // Controlling X axis
            val xAxisForm = fecesFormHistoryChart?.xAxis
            // Set the xAxis position to bottom. Default is top
            xAxisForm?.position = XAxis.XAxisPosition.BOTTOM
            //Customizing left axis value
            val leftAxisForm = fecesFormHistoryChart?.axisLeft
            leftAxisForm?.granularity = 1f // minimum axis-step (interval) is 1
            leftAxisForm?.valueFormatter = MyXAxisFormatterForm()
            fecesFormHistoryChart?.invalidate()
//        fecesColorHistoryChart?.animateY(1500,Easing.EaseInOutQuart)
            fecesFormHistoryChart?.animateX(3500, Easing.EaseInOutQuad)
            /* End feces form history chart*/
        }
    }

    private fun showFecesColorHistory(){
        mainViewModel.getFecesColorData().observe(viewLifecycleOwner){
            val dataFc: ArrayList<Entry> = ArrayList()
            var i = 1f
            for (data in (0..it.size-1).reversed()){
                dataFc.add((Entry(i, (it[data].type?.toFloat()?.plus(1) ?: 0) as Float)))
                Log.d("TAG", "onViewCreated1: $data")
                i++
            }
            val fecesColorHistoryChart = getView()?.findViewById<LineChart>(R.id.sc_history_feces_color)
            val lineFecesColorDataset = LineDataSet(dataFc,"ok")
            lineFecesColorDataset.setDrawFilled(true)
            lineFecesColorDataset.fillDrawable = ContextCompat.getDrawable(requireActivity(), R.drawable.gradient)
            val iLineDatasetFecesColor = ArrayList<ILineDataSet>()
            iLineDatasetFecesColor.add(lineFecesColorDataset)
            val lineFecesColorData = LineData(iLineDatasetFecesColor)
            fecesColorHistoryChart?.data = lineFecesColorData
//        fecesColorHistoryChart?.setVisibleXRangeMaximum(20f)
            fecesColorHistoryChart?.setMaxVisibleValueCount(1)
            fecesColorHistoryChart?.setVisibleXRange(0f,13f)
            fecesColorHistoryChart?.description?.isEnabled = false
            fecesColorHistoryChart?.setDrawGridBackground(false)
//        fecesColorHistoryChart?.xAxis?.isEnabled = false
//        fecesColorHistoryChart?.axisLeft?.isEnabled = false
            fecesColorHistoryChart?.axisRight?.isEnabled = false
//        fecesColorHistoryChart?.setVisibleYRange(0f,6f, YAxis.AxisDependency.RIGHT)
            fecesColorHistoryChart?.isDragXEnabled = false
            fecesColorHistoryChart?.isScaleXEnabled = false
//        fecesColorHistoryChart?.setDrawEntryLabels(false)
            fecesColorHistoryChart?.legend?.isEnabled = false
            // Controlling X axis
            val xAxis = fecesColorHistoryChart?.xAxis
            // Set the xAxis position to bottom. Default is top
            xAxis?.position = XAxis.XAxisPosition.BOTTOM
            //Customizing left axis value
            val leftAxis = fecesColorHistoryChart?.axisLeft
            leftAxis?.granularity = 1f // minimum axis-step (interval) is 1
            leftAxis?.valueFormatter = MyXAxisFormatterColor()
            fecesColorHistoryChart?.invalidate()
//        fecesColorHistoryChart?.animateY(1500,Easing.EaseInOutQuart)
            fecesColorHistoryChart?.animateX(3500, Easing.EaseInOutQuad)
            /* End feces color chart */

            //Date integration
            val tvDateOldest = view?.findViewById<TextView>(R.id.tv_oldest_feces_data)
            val tvDateNewest = view?.findViewById<TextView>(R.id.tv_newest_feces_data)

            if (it[it.size-1].createdAt != "-"){
                val inst: OffsetDateTime = OffsetDateTime.ofInstant(
                    Instant.parse(it[it.size-1].createdAt),
                    ZoneId.systemDefault()
                )
                val dataTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(inst)
                tvDateNewest?.text = "Data terbaru: $dataTime"
//                Log.d("time1", "onViewCreated: $dataTime")
            } else {
                tvDateNewest?.text = "Data terbaru: -"
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