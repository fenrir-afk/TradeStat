package com.example.presentation.ui.analysis

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.presentation.R
import com.example.presentation.databinding.ActivityAnalysisBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class AnalysisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalysisBinding
    private val analysisViewModel:AnalysisViewModel by viewModels()
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
        lifecycleScope.launch(Dispatchers.Main){
            repeatOnLifecycle(Lifecycle.State.STARTED){
                analysisViewModel.entryListFlow
                    .filter { it.isNotEmpty() }
                    .collect{
                        updateChart(it,binding.lineChart)
                        binding.resultText.text = getString(R.string.trade_result, analysisViewModel.tradeResult)
                        binding.strategyText.text = resources.getString(R.string.best_strategy,analysisViewModel.bestStrategy)
                        binding.instrumentText.text = resources.getString(R.string.best_instrument,analysisViewModel.bestInstrument)
                }
            }
        }
    }
    private fun updateChart(list: MutableList<Entry>, chart: LineChart){

        val lineDataSet = LineDataSet(list,"Victory/defeat")
        lineDataSet.valueTextColor = ContextCompat.getColor(this, R.color.lightGray)
        val dataSets:MutableList<LineDataSet> = mutableListOf()
        dataSets.add(lineDataSet)
        val data = LineData(dataSets as List<ILineDataSet>?)


        // Getting left axis
        val leftAxis = chart.axisLeft
        leftAxis.textColor = ContextCompat.getColor(this, R.color.lightGray)

        // Getting right axis
        val rightAxis = chart.axisRight
        rightAxis.textColor = ContextCompat.getColor(this, R.color.lightGray)

        val xAxis = chart.xAxis
        xAxis.textColor = ContextCompat.getColor(this, R.color.lightGray)

        val legend = chart.legend
        legend.textColor = ContextCompat.getColor(this, R.color.lightGray)

        val description = Description()
        description.text = ""
        description.textColor = ContextCompat.getColor(this,R.color.lightGray)

        chart.data = data
        chart.setNoDataText("No data")
        chart.setNoDataTextColor(ContextCompat.getColor(this,R.color.lightGray))
        chart.description = description
        chart.invalidate()
    }
}