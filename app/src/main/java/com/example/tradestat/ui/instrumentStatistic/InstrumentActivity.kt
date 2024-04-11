package com.example.tradestat.ui.instrumentStatistic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.R
import com.example.tradestat.databinding.ActivityInstrumentBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


class InstrumentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInstrumentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstrumentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val instrumentViewModel =
            ViewModelProvider(this)[InstrumentViewModel::class.java]
        instrumentViewModel.getWinRateList.observe(this) {
            setChart(it,instrumentViewModel.instrumentsNames)
        }
    }
    private fun setChart(winRateList: List<Int>, instrumentsNames: MutableList<String>) {
        val chart = binding.chart2
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(false)
        xAxis.isEnabled = true
        xAxis.labelCount = instrumentsNames.size
        xAxis.textColor = ContextCompat.getColor(this, R.color.lightGray)
        xAxis.setTextSize(11f);


        val yLeft = chart.axisLeft
        yLeft.axisMaximum = 100f
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = true
        yLeft.textColor = ContextCompat.getColor(this, R.color.lightGray)
        yLeft.setTextSize(11f);


        val  yRight = chart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.isEnabled = true
        yRight.textColor = ContextCompat.getColor(this, R.color.lightGray)
        yRight.setTextSize(11f);


        chart.setNoDataText("No data")
        chart.setNoDataTextColor(resources.getColor(R.color.lightGray))


        chart.setFitBars(true)
        chart.animateY(2500)

        val formatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return instrumentsNames[value.toInt()]
            }
        }


        xAxis.valueFormatter = formatter
        xAxis.setGranularity(1f)
        xAxis.isGranularityEnabled = true


        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);


        setData(winRateList)
        chart.invalidate()
    }
    private fun setData(winRateList: List<Int>) {
        val values = ArrayList<BarEntry>()
        repeat(winRateList.size) {
            values.add(BarEntry(it.toFloat(), winRateList[it].toFloat()))
        }
        val barDataSet = BarDataSet(values, "")
        val dataSets = ArrayList<IBarDataSet>()
        barDataSet.valueTextColor = ContextCompat.getColor(this, R.color.white)

        dataSets.add(barDataSet)

        val barData = BarData(dataSets)
        barData.setValueTextSize(10f)
        barData.barWidth = 0.5f

        binding.chart2.data = barData
    }



}