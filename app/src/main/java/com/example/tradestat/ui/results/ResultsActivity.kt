package com.example.tradestat.ui.results

import android.graphics.Color
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
        updateChart()
    }

    private fun updateChart() {
        var arr1 = ArrayList<RadarEntry>()
        arr1.add(RadarEntry(420F))
        arr1.add(RadarEntry(475F))
        arr1.add(RadarEntry(508F))
        arr1.add(RadarEntry(660F))
        arr1.add(RadarEntry(550F))
        arr1.add(RadarEntry(630F))
        arr1.add(RadarEntry(470F))
        var dataSet1 = RadarDataSet(arr1,"Dataset 1")
        dataSet1.color = getColor(R.color.purple_200)
        dataSet1.lineWidth = 2F
        dataSet1.valueTextColor = getColor(R.color.purple_200)
        dataSet1.fillColor = getColor(R.color.purple_200)
        dataSet1.valueTextSize = 14f
        dataSet1.setDrawFilled(true)

        var arr2 = ArrayList<RadarEntry>()
        arr2.add(RadarEntry(310F))
        arr2.add(RadarEntry(420F))
        arr2.add(RadarEntry(685F))
        arr2.add(RadarEntry(820F))
        arr2.add(RadarEntry(490F))
        arr2.add(RadarEntry(730F))
        arr2.add(RadarEntry(200F))
        var dataSet2 = RadarDataSet(arr2,"Dataset 1")
        dataSet2.color = getColor(R.color.green)
        dataSet2.lineWidth = 2F
        dataSet2.valueTextColor =getColor(R.color.green)
        dataSet2.fillColor = getColor(R.color.green)
        dataSet2.valueTextSize = 14f
        dataSet2.setDrawFilled(true)

        var radarData = RadarData()
        radarData.addDataSet(dataSet1)
        radarData.addDataSet(dataSet2)

        var labels = listOf("2014","2015","2016","2017","2018","2019","2020")

        binding.chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.chart.xAxis.textColor = getColor(R.color.white)
        binding.chart.yAxis.isEnabled = false
        binding.chart.description.text = ""
        binding.chart.data = radarData






    }
}