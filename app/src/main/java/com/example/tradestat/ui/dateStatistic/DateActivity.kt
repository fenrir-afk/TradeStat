package com.example.tradestat.ui.dateStatistic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.R
import com.example.tradestat.data.model.DaysOfWeek
import com.example.tradestat.databinding.ActivityDateBinding
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
        val dateViewModel = ViewModelProvider(this)[DateViewModel::class.java]
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
            binding.mondayText.text = "${DaysOfWeek.Monday} ${it[0]}%"
            binding.tuesdayText.text = "${DaysOfWeek.Tuesday} ${it[1]}%"
            binding.wednesdayText.text = "${DaysOfWeek.Wednesday} ${it[2]}%"
            binding.thursdayText.text = "${DaysOfWeek.Thursday} ${it[3]}%"
            binding.fridayText.text = "${DaysOfWeek.Friday} ${it[4]}%"
            binding.saturdayText.text = "${DaysOfWeek.Saturday} ${it[5]}%"
            binding.sundayText.text = "${DaysOfWeek.Sunday} ${it[6]}%"

        }
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