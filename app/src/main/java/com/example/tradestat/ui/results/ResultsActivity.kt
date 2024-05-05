package com.example.tradestat.ui.results

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.R
import com.example.tradestat.databinding.ActivityResultsBinding
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.Calendar
import java.util.Locale

class ResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding
    private lateinit var resultsViewModel: ResultsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultsViewModel = ViewModelProvider(this)[ResultsViewModel::class.java]
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        resultsViewModel.currentMonthStrategiesRating.observe(this){
            updateChart(it,resultsViewModel.previousMonthStrategiesRating,resultsViewModel.namesList)
        }
    }
    /**
    *In this method we are getting the months by number.
    * */
    private fun getMonthName(month: Int): String {
        val monthNames = listOf(
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        )
        return monthNames[month]
    }

    private fun updateChart(
        currentMonthStrategiesRating: MutableList<Int>,
        previousMonthStrategiesRating: MutableList<Int>,
        labels: MutableSet<String>
    ) {
        var arr1 = ArrayList<RadarEntry>()
        currentMonthStrategiesRating.forEach {
            arr1.add(RadarEntry(it.toFloat()))
        }
        val calendar = Calendar.getInstance()
        val currentMonth = getMonthName(calendar.get(Calendar.MONTH))
        val previousMonth = getMonthName(calendar.get(Calendar.MONTH) - 1)
        var dataSet1 = RadarDataSet(arr1,currentMonth)
        dataSet1.color = getColor(R.color.purple_200)
        dataSet1.lineWidth = 2F
        dataSet1.valueTextColor = getColor(R.color.purple_200)
        dataSet1.fillColor = getColor(R.color.purple_200)
        dataSet1.valueTextSize = 14f
        dataSet1.setDrawFilled(true)

        var arr2 = ArrayList<RadarEntry>()
        previousMonthStrategiesRating.forEach {
            arr2.add(RadarEntry(it.toFloat()))
        }
        var dataSet2 = RadarDataSet(arr2,previousMonth)
        dataSet2.color = getColor(R.color.green)
        dataSet2.lineWidth = 2F
        dataSet2.valueTextColor =getColor(R.color.green)
        dataSet2.fillColor = getColor(R.color.green)
        dataSet2.valueTextSize = 14f
        dataSet2.setDrawFilled(true)

        var radarData = RadarData()
        radarData.addDataSet(dataSet1)
        radarData.addDataSet(dataSet2)
        binding.chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.chart.xAxis.textColor = getColor(R.color.white)
        binding.chart.yAxis.axisMinimum = 0f
        binding.chart.yAxis.axisMaximum = 110f
        binding.chart.xAxis.axisMinimum = 0f
        binding.chart.xAxis.axisMaximum = 110f
        binding.chart.description.text = ""
        binding.chart.data = radarData
        binding.chart.legend.textColor = getColor(R.color.white)

        binding.chart.xAxis.setDrawAxisLine(false)
        binding.chart.xAxis.setDrawLabels(true)
        binding.chart.yAxis.setDrawAxisLine(false)
        binding.chart.yAxis.setDrawLabels(false)


    }
}