package com.example.tradestat.ui.dateStatistic

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.R
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.databinding.ActivityDateBinding
import com.example.tradestat.repository.TradesRepository
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class DateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        setSupportActionBar(binding.include.myToolBar)


        val repository = TradesRepository(TradeDatabase.getDatabase(application))
        val viewModelProvideFactory = DateViewModelFactory(Application(),repository)
        val dateViewModel = ViewModelProvider(this,viewModelProvideFactory)[DateViewModel::class.java]


        dateViewModel.updateDay()
        dateViewModel.getRatingList()
        dateViewModel.mondayList.observe(this) {
            updateChart(it, binding.mondayChart)
        }
        dateViewModel.tuesdayList.observe(this) {
            updateChart(it, binding.tuesdayChart)
        }
        dateViewModel.wednesdayList.observe(this) {
            updateChart(it, binding.wednesdayChart)
        }
        dateViewModel.thursdayList.observe(this) {
            updateChart(it, binding.thursdayChart)
        }
        dateViewModel.fridayList.observe(this) {
            updateChart(it, binding.fridayChart)
        }
        dateViewModel.saturdayList.observe(this) {
            updateChart(it, binding.saturdayChart)
        }
        dateViewModel.sundayList.observe(this) {
            updateChart(it, binding.sundayChart)
        }
        dateViewModel.RatingList.observe(this) {
            binding.mondayText.text = resources.getString(R.string.monday) +  " — ${it[0]}%"
            binding.tuesdayText.text = resources.getString(R.string.tuesday) + " — ${it[1]}%"
            binding.wednesdayText.text =resources.getString(R.string.wednesday) + " — ${it[2]}%"
            binding.thursdayText.text =resources.getString(R.string.thursday) +  " — ${it[3]}%"
            binding.fridayText.text =resources.getString(R.string.friday)  + " — ${it[4]}%"
            binding.saturdayText.text =resources.getString(R.string.saturday) +" — ${it[5]}%"
            binding.sundayText.text =resources.getString(R.string.sunday) + " — ${it[6]}%"

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
        description.textColor = resources.getColor(R.color.lightGray)

        chart.data = data
        chart.setNoDataText("No data")
        chart.setNoDataTextColor(resources.getColor(R.color.lightGray))
        chart.description = description
        chart.invalidate()
    }
}