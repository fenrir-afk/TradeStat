package com.example.tradestat.ui.strategyStatistic

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tradestat.R
import com.example.tradestat.databinding.ActivityStrategyBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import java.util.ArrayList
import java.util.Arrays

class StrategyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStrategyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStrategyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setChart()

    }



    private fun setChart() {
        // Получение левой оси
        val leftAxis = binding.chart.axisLeft
        leftAxis.textColor = ContextCompat.getColor(this, R.color.lightGray)

        // Получение правой оси
        val rightAxis = binding.chart.axisRight
        rightAxis.textColor = ContextCompat.getColor(this, R.color.lightGray)

        val xAxis = binding.chart.xAxis
        xAxis.textColor = ContextCompat.getColor(this, R.color.lightGray)

        val legend = binding.chart.legend
        legend.textColor = ContextCompat.getColor(this, R.color.lightGray)

        val description = Description()
        description.text = ""
        description.textColor = resources.getColor(R.color.lightGray)


        var list1 = ArrayList<Entry>();
        list1.add(Entry(0f,10f))
        list1.add(Entry(1f,10f))
        list1.add(Entry(2f,15f))
        list1.add(Entry(3f,20f))

        var list2 = ArrayList<Entry>();
        list2.add(Entry(0f,5f))
        list2.add(Entry(1f,20f))
        list2.add(Entry(2f,25f))
        list2.add(Entry(3f,30f))


        val lineDataSet1 = LineDataSet(list1,"First")
        lineDataSet1.color = Color.GREEN
        lineDataSet1.setCircleColor(Color.GREEN)
        lineDataSet1.valueTextColor = Color.GREEN

        val lineDataSet2 = LineDataSet(list2,"Second")


        val data = LineData(lineDataSet1,lineDataSet2)

        binding.chart.data = data
        binding.chart.setNoDataText("No data")
        binding.chart.setNoDataTextColor(resources.getColor(R.color.lightGray))
        binding.chart.description = description
        binding.chart.invalidate()
    }
}