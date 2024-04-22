package com.example.tradestat.ui.strategyStatistic

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.R
import com.example.tradestat.databinding.ActivityStrategyBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.ArrayList
import java.util.Random

class StrategyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStrategyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStrategyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var strategyViewModel = ViewModelProvider(this)[StrategyViewModel::class.java]
        strategyViewModel.entriesList.observe(this){
            setChart(strategyViewModel.strategiesNames,it)
        }
    }



    private fun setChart(strategiesNames: MutableList<String>, lists: MutableList<List<Entry>>) {
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
        var lineDataSetList = mutableListOf<LineDataSet>()
        var colorArr = getColorArr(strategiesNames)

       lists.forEachIndexed { index, entries ->
           val lineDataSet1 = LineDataSet(entries,strategiesNames[index])
           lineDataSet1.color = colorArr[index]
           lineDataSet1.setCircleColor(colorArr[index])
           lineDataSet1.valueTextColor = colorArr[index]
           lineDataSetList.add(lineDataSet1)
       }

        val data = LineData(lineDataSetList as List<ILineDataSet>?)

        binding.chart.data = data
        binding.chart.setNoDataText("No data")
        binding.chart.setNoDataTextColor(resources.getColor(R.color.lightGray))
        binding.chart.description = description
        binding.chart.invalidate()
    }
    private fun getColorArr(strategiesNames: MutableList<String>): MutableList<Int> {
        val colors = mutableListOf<Int>()
        val rnd = Random()
        for (i in strategiesNames.indices){
            colors.add(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)))
        }
        return colors
    }
}