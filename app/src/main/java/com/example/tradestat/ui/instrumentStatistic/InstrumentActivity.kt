package com.example.tradestat.ui.instrumentStatistic

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
import com.example.tradestat.databinding.ActivityInstrumentBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


class InstrumentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInstrumentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstrumentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        setSupportActionBar(binding.include.myToolBar)
        binding.ratingImageView.setColorFilter(ContextCompat.getColor(this, R.color.MediumGray))
        val instrumentViewModel =
            ViewModelProvider(this)[InstrumentViewModel::class.java]
        binding.ratingImageView.setColorFilter(ContextCompat.getColor(this, R.color.MediumGray))
        instrumentViewModel.getData()
        instrumentViewModel.getWinRateList.observe(this) {
            setChart(it,instrumentViewModel.instrumentsNames,1)
        }
        instrumentViewModel.getWinRateListShort.observe(this) {
            setTexts(it,instrumentViewModel.getWinRateListLong,instrumentViewModel.instrumentsNames,1)
        }
        binding.amountCard.setOnClickListener{
            binding.ratingImageView.setColorFilter(ContextCompat.getColor(this, R.color.black_grey))
            binding.amountImageView.setColorFilter(ContextCompat.getColor(this, R.color.MediumGray))
            binding.textView.text = "Graph of number of trades"

            setChart(instrumentViewModel.tradeNumbers,instrumentViewModel.instrumentsNames,2)
            setTexts(instrumentViewModel.tradeShortNumbers,instrumentViewModel.tradeLongNumbers,instrumentViewModel.instrumentsNames,2)
        }
        binding.ratingCard.setOnClickListener{
            binding.ratingImageView.setColorFilter(ContextCompat.getColor(this, R.color.MediumGray))
            binding.amountImageView.setColorFilter(ContextCompat.getColor(this, R.color.black_grey))
            binding.textView.text = "Graph of instrument rating"

            setChart(instrumentViewModel.getWinRateList.value!!,instrumentViewModel.instrumentsNames,1)
            setTexts(instrumentViewModel.getWinRateListShort.value!!,instrumentViewModel.getWinRateListLong,instrumentViewModel.instrumentsNames,1)
        }
    }
    /**
     * In this method we generate cards with texts which represent long and short trades numbers and rating
     * @param winRateListShort number of all trades which direction is short and result is Victory
     * @param winRateListLong number of all trades which direction is Long and result is Victory
     * @param instrumentsNames names of all instruments
     * @param token int value that help us to change rating mode of cards and amount trades mode of cards
     * */
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
    /**
     * In this method we generate cards for setTexts()
     * @param string  string value for the text in the card
     * @param counter  int value for changing params for different card in aa row. 1 is the 2 and 3. Another number represents first card
     * */
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
    /**
     * In this method we provide data to the chart
     * @param instrumentsNames names of instruments
     * @param baseList list of rating of each instruments (in this case it also for each horizontal bar)
     * @param token is the int value. We need no change value  and rating mode of the graph
     * */
    private fun setChart(baseList: List<Int>, instrumentsNames: MutableList<String>, token: Int) {
        val chart = binding.chart2
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(false)
        xAxis.isEnabled = true
        xAxis.labelCount = instrumentsNames.size
        xAxis.textColor = Color.WHITE
        xAxis.setTextSize(13f);
        val typeFace: Typeface? = ResourcesCompat.getFont(this.applicationContext, R.font.oxygen)
        xAxis.typeface = typeFace



        val yLeft = chart.axisLeft
        if (token==1){
            yLeft.axisMaximum = 110f
        }else{
            var mySize = 0
            for (i in baseList){
                mySize+=i
            }
            yLeft.axisMaximum = mySize.toFloat()
        }
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = true
        yLeft.textColor = Color.WHITE
        yLeft.setTextSize(11f);


        val  yRight = chart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.isEnabled = true
        yRight.textColor = Color.WHITE
        yRight.setTextSize(11f);


        chart.setNoDataText("No data")
        chart.setNoDataTextColor(resources.getColor(R.color.lightGray))


        chart.setFitBars(true)
        chart.animateY(2500)

        val formatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return instrumentsNames[value.toInt()]
            }
        }


        xAxis.valueFormatter = formatter
        xAxis.setGranularity(1f)
        xAxis.isGranularityEnabled = true


        chart.xAxis.setDrawGridLines(false);
        chart.axisLeft.setDrawGridLines(false);
        chart.axisRight.setDrawGridLines(false);


        setData(baseList)
        chart.invalidate()
    }
    /**
     * In this method we set data to the graph
     * */
    private fun setData(winRateList: List<Int>) {
        val values = ArrayList<BarEntry>()
        repeat(winRateList.size) {
            values.add(BarEntry(it.toFloat(), winRateList[it].toFloat()))
        }
        val barDataSet = BarDataSet(values, "")
        val dataSets = ArrayList<IBarDataSet>()
        barDataSet.valueTextColor = ContextCompat.getColor(this, R.color.white)

        dataSets.add(barDataSet)

        val barData = BarData(dataSets)
        barData.setValueTextSize(10f)
        barData.barWidth = 0.5f

        binding.chart2.data = barData
    }



}