package com.example.tradestat.ui.dateStatistic

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tradestat.R
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.databinding.ActivityDateBinding
import com.example.tradestat.repository.TradesRepository
import com.example.tradestat.utils.BaseViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class DateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDateBinding
    private val dateViewModel:DateViewModel by viewModels {
        val repository = TradesRepository(TradeDatabase.getDatabase(this))
        BaseViewModelFactory(repository, Application())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        setSupportActionBar(binding.include.myToolBar)
        setFlowCollectors()
    }

    private fun setFlowCollectors(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                dateViewModel.combinedDayFlow.collect{
                    it.forEachIndexed{index, dayEntries ->
                        when(index){
                            0 -> updateChart(dayEntries, binding.mondayChart)
                            1 -> updateChart(dayEntries, binding.tuesdayChart)
                            2 -> updateChart(dayEntries, binding.wednesdayChart)
                            3 -> updateChart(dayEntries, binding.thursdayChart)
                            4 -> updateChart(dayEntries, binding.fridayChart)
                            5 -> updateChart(dayEntries, binding.saturdayChart)
                            6 -> updateChart(dayEntries, binding.sundayChart)
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                dateViewModel.ratingFlow.filter { it.isNotEmpty() }.collect{ratings->    //filter cause the start value is emptyList
                    binding.mondayText.text = getString(R.string.monday_with_rating, ratings[0])
                    binding.tuesdayText.text = getString(R.string.tuesday_with_rating, ratings[1])
                    binding.wednesdayText.text = getString(R.string.wednesday_with_rating, ratings[2])
                    binding.thursdayText.text = getString(R.string.thursday_with_rating, ratings[3])
                    binding.fridayText.text = getString(R.string.friday_with_rating, ratings[4])
                    binding.saturdayText.text = getString(R.string.saturday_with_rating, ratings[5])
                    binding.sundayText.text = getString(R.string.sunday_with_rating, ratings[6])
                }
            }
        }
    }
    /**
     * In this method we provide data for each graph which represents days of week
     * @param list coordinates for the graph
     * @param chart view in which we provide data
     * */
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
        description.textColor = ContextCompat.getColor(this,R.color.lightGray)

        chart.data = data
        chart.setNoDataText("No data")
        chart.setNoDataTextColor(ContextCompat.getColor(this,R.color.lightGray))
        chart.description = description
        chart.invalidate()
    }
}