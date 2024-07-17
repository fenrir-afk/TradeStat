package com.example.tradestat.ui.analysis

import android.app.Application
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.tradestat.R
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.databinding.ActivityAnalysisBinding
import com.example.tradestat.repository.TradesRepository
import com.example.tradestat.utils.BaseViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class AnalysisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalysisBinding
    private val analysisViewModel:AnalysisViewModel by viewModels {
        val repository = TradesRepository(TradeDatabase.getDatabase(this))
        BaseViewModelFactory(repository, Application())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalysisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        setSupportActionBar(binding.include.myToolBar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        analysisViewModel.updateData()
        analysisViewModel.list.observe(this, Observer {
            updateChart(it,binding.lineChart)
            binding.resultText.text = resources.getString(R.string.trade_result) + analysisViewModel.tradeResult.toString() + getString(R.string.wins_defeats)
            binding.strategyText.text = resources.getString(R.string.best_strategy) + analysisViewModel.bestStrategy
            binding.instrumentText.text = resources.getString(R.string.best_instrument) + analysisViewModel.bestInstrument
        })
    }
    private fun updateChart(list: MutableList<Entry>, chart: LineChart){

        val lineDataSet = LineDataSet(list,"Victory/defeat")
        lineDataSet.valueTextColor = ContextCompat.getColor(this, R.color.lightGray)
        val dataSets:MutableList<LineDataSet> = mutableListOf()
        dataSets.add(lineDataSet)
        val data = LineData(dataSets as List<ILineDataSet>?)


        // Получение левой оси
        val leftAxis = chart.axisLeft
        leftAxis.textColor = ContextCompat.getColor(this, R.color.lightGray)

        // Получение правой оси
        val rightAxis = chart.axisRight
        rightAxis.textColor = ContextCompat.getColor(this, R.color.lightGray)

        val xAxis = chart.xAxis
        xAxis.textColor = ContextCompat.getColor(this, R.color.lightGray)

        val legend = chart.legend
        legend.textColor = ContextCompat.getColor(this, R.color.lightGray)

        val description = Description()
        description.text = ""
        description.textColor = resources.getColor(R.color.lightGray)

        chart.data = data
        chart.setNoDataText("No data")
        chart.setNoDataTextColor(resources.getColor(R.color.lightGray))
        chart.description = description
        chart.invalidate()
    }
}