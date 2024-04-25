package com.example.tradestat.ui.strategyStatistic

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.R
import com.example.tradestat.databinding.ActivityStrategyBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.Random

class StrategyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStrategyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStrategyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        setSupportActionBar(binding.myToolBar)
        val strategyViewModel = ViewModelProvider(this)[StrategyViewModel::class.java]
        binding.ratingImageView.setColorFilter(ContextCompat.getColor(this, R.color.MediumGray))
        strategyViewModel.entriesList.observe(this){
            setChart(strategyViewModel.strategiesNames,it)
        }
        strategyViewModel.updateData()
        strategyViewModel.getWinRateListShort.observe(this) {
            setTexts(it,strategyViewModel.getWinRateListLong,strategyViewModel.strategiesNames,1)
        }
        binding.amountCard.setOnClickListener{
            binding.ratingImageView.setColorFilter(ContextCompat.getColor(this, R.color.black_grey))
            binding.amountImageView.setColorFilter(ContextCompat.getColor(this, R.color.MediumGray))
            binding.textView.text = "Graph of number of trades"
            setTexts(strategyViewModel.tradeShortNumbers,strategyViewModel.tradeLongNumbers,strategyViewModel.strategiesNames,2)
        }
        binding.ratingCard.setOnClickListener{
            binding.ratingImageView.setColorFilter(ContextCompat.getColor(this, R.color.MediumGray))
            binding.amountImageView.setColorFilter(ContextCompat.getColor(this, R.color.black_grey))
            binding.textView.text = "Graph of strategies rating"
            setTexts(strategyViewModel.getWinRateListShort.value!!,strategyViewModel.getWinRateListLong,strategyViewModel.strategiesNames,1)
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
        val lineDataSetList = mutableListOf<LineDataSet>()
        val colorArr = getColorArr(strategiesNames)

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
            colors.add(Color.argb(255, 80 + rnd.nextInt(176), 80 + rnd.nextInt(176), 80 + rnd.nextInt(176)))

        }
        return colors
    }
    private fun setTexts(
        winRateListShort: List<Int>,
        winRateListLong: MutableList<Int>,
        instrumentsNames: MutableList<String>,
        token: Int
    ){
        binding.parentLayout.removeAllViews()
        for (i in instrumentsNames.size-1 downTo  0){
            val layout = LinearLayout(this)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // CardView width
                LinearLayout.LayoutParams.MATCH_PARENT // CardView height
            )
            layout.layoutParams = layoutParams
            layout.orientation = LinearLayout.HORIZONTAL
            layout.setBackgroundColor(resources.getColor(R.color.black_grey))
            if (token == 1){
                layout.addView( createText("${instrumentsNames[i]}: ",1))
                layout.addView( createText("Short rate:${winRateListShort[i]}%",0))
                layout.addView( createText("Long rate:${winRateListLong[i]}%",0))
            }else{
                layout.addView( createText("${instrumentsNames[i]}: ",1))
                layout.addView( createText("Short number:${winRateListShort[i]}",0))
                layout.addView( createText("Long number:${winRateListLong[i]}",0))
            }

            val card = CardView(this)
            val cardParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                resources.getDimension(R.dimen.instrument_card_height).toInt()
            )
            cardParams.setMargins(resources.getDimension(R.dimen.base_start_maring).toInt(), resources.getDimension(R.dimen.base_start_maring).toInt(), 0, 0)
            card.layoutParams = cardParams
            card.radius = resources.getDimension(R.dimen.card_corner_radius)
            card.addView(layout)
            binding.parentLayout.addView(card)

        }
    }
    private fun createText(string: String,counter:Int): TextView {
        val text = TextView(this)
        var textParams = LinearLayout.LayoutParams(
            resources.getDimension(R.dimen.instrumentCard_Basetext_width).toInt(), // CardView width
            LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
        )
        if (counter == 1){
            textParams = LinearLayout.LayoutParams(
                resources.getDimension(R.dimen.instrumentCard_text_width).toInt(), // CardView width
                LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
            )
            textParams.setMargins(resources.getDimension(R.dimen.base_start_maring).toInt(), 0, 0, 0)
        }
        textParams.gravity = Gravity.CENTER_VERTICAL
        text.layoutParams = textParams
        text.textSize = 13.5f
        text.setTextColor(Color.WHITE)
        val typeFace: Typeface? = ResourcesCompat.getFont(this.applicationContext, R.font.oxygen)
        text.typeface = typeFace
        text.text = string
        return text
    }

}