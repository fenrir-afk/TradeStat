package com.example.tradestat.ui.strategyStatistic

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tradestat.R
import com.example.tradestat.databinding.ActivityStrategyBinding
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry

class StrategyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStrategyBinding
    val profileValues = ArrayList<RadarEntry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStrategyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataListing()
    }

    private fun dataListing() {
        profileValues.add(RadarEntry(150f))
        profileValues.add(RadarEntry(163f))
        profileValues.add(RadarEntry(120f))
        profileValues.add(RadarEntry(3150f))
        profileValues.add(RadarEntry(165f))
        setChart()
    }

    private fun setChart() {
        val dataSet = RadarDataSet(profileValues,"Radar chard legend")
        dataSet.valueTextColor = Color.GRAY
        dataSet.valueTextSize = 12f
        dataSet.fillColor = resources.getColor(R.color.darkWhite)
        dataSet.color = resources.getColor(R.color.darkWhite)
        dataSet.lineWidth = 4f
        dataSet.setDrawHighlightIndicators(true)
        dataSet.setDrawFilled(true)
        dataSet.setDrawValues(true)

        val data = RadarData(dataSet)
        data.setValueTextColor(Color.GRAY)
        binding.bubbleChart.data = data
        binding.bubbleChart.invalidate()
        binding.bubbleChart.legend.isEnabled = false
        binding.bubbleChart.description.isEnabled = false
        binding.bubbleChart.setNoDataText("No data available")
        binding.bubbleChart.animateXY(1400,1400)
        binding.bubbleChart.setTouchEnabled(true)
        binding.bubbleChart.setBackgroundColor(Color.BLACK)
        binding.bubbleChart.webColor = Color.WHITE
        binding.bubbleChart.xAxis.textColor = Color.GRAY
        binding.bubbleChart.yAxis.textColor = Color.GRAY

    }
}