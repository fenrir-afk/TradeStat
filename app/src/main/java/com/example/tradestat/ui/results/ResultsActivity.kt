package com.example.tradestat.ui.results

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tradestat.R
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.databinding.ActivityResultsBinding
import com.example.tradestat.repository.TradesRepository
import com.example.tradestat.utils.BaseViewModelFactory
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.util.Calendar


class ResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding
    private  val resultsViewModel: ResultsViewModel by viewModels {
        val repository = TradesRepository(TradeDatabase.getDatabase(this))
        BaseViewModelFactory(repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.include2.myToolBar)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)

        resultsViewModel.updateLists()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.ratingImageView.setColorFilter(ContextCompat.getColor(this, R.color.MediumGray))
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                resultsViewModel.currentMonthStrategiesRatingFlow.filter { it.isNotEmpty() }.collect{
                    updateChart(it,resultsViewModel.previousMonthStrategiesRating,resultsViewModel.strategiesNames)
                    setTexts(it, resultsViewModel.previousMonthStrategiesRating, resultsViewModel.strategiesNames.toMutableList())
                }
            }
        }
        binding.amountCard.setOnClickListener{
            binding.ratingImageView.setColorFilter(ContextCompat.getColor(this, R.color.background))
            binding.amountImageView.setColorFilter(ContextCompat.getColor(this, R.color.MediumGray))
            updateChart(resultsViewModel.currentMonthInstrumentsRating,resultsViewModel.previousMonthInstrumentsRating,resultsViewModel.instrumentNames)
            setTexts(resultsViewModel.currentMonthInstrumentsRating, resultsViewModel.previousMonthInstrumentsRating, resultsViewModel.instrumentNames.toMutableList())
        }
        binding.ratingCard.setOnClickListener{
            binding.ratingImageView.setColorFilter(ContextCompat.getColor(this, R.color.MediumGray))
            binding.amountImageView.setColorFilter(ContextCompat.getColor(this, R.color.background))
            updateChart(resultsViewModel.currentMonthStrategiesRatingFlow.value,resultsViewModel.previousMonthStrategiesRating,resultsViewModel.strategiesNames)
            setTexts(resultsViewModel.currentMonthStrategiesRatingFlow.value, resultsViewModel.previousMonthStrategiesRating, resultsViewModel.strategiesNames.toMutableList())
        }
    }
    /**
    *In this method we are getting the months by number.
    * */
    private fun getMonthName(month: Int): String {
        val monthNames = resources.getStringArray(R.array.month_names)
        return monthNames[month - 1]
    }

    private fun updateChart(
        currentMonthStrategiesRating: MutableList<Int>,
        previousMonthStrategiesRating: MutableList<Int>,
        labels: MutableSet<String>
    ) {
        val arr1 = ArrayList<RadarEntry>()
        currentMonthStrategiesRating.forEach {
            arr1.add(RadarEntry(it.toFloat()))
        }
        val calendar = Calendar.getInstance()
        val currentMonth = getMonthName(calendar.get(Calendar.MONTH)+1)
        val previousMonth = getMonthName(calendar.get(Calendar.MONTH) )
        val dataSet1 = RadarDataSet(arr1,currentMonth)
        dataSet1.color = getColor(R.color.purple_200)
        dataSet1.lineWidth = 2F
        dataSet1.valueTextColor = getColor(R.color.purple_200)
        dataSet1.fillColor = getColor(R.color.purple_200)
        dataSet1.valueTextSize = 14f
        dataSet1.setDrawFilled(true)

        val arr2 = ArrayList<RadarEntry>()
        previousMonthStrategiesRating.forEach {
            arr2.add(RadarEntry(it.toFloat()))
        }
        val dataSet2 = RadarDataSet(arr2,previousMonth)
        dataSet2.color = getColor(R.color.green)
        dataSet2.lineWidth = 2F
        dataSet2.valueTextColor =getColor(R.color.green)
        dataSet2.fillColor = getColor(R.color.green)
        dataSet2.valueTextSize = 14f
        dataSet2.setDrawFilled(true)

        val radarData = RadarData()
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
        binding.chart.animateXY(1000,1400)
        binding.chart.invalidate()
    }
    /**
     * In this method we are creating cards with 2 texts and 1 image (arrow)
     * */
    private fun setTexts(
        currentRating: List<Int>,
        previousRating: MutableList<Int>,
        names: MutableList<String>
    ){
        binding.parentLayout.removeAllViews()
        for (i in names.size-1 downTo  0){
            val layout = LinearLayout(this)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // CardView width
                LinearLayout.LayoutParams.MATCH_PARENT // CardView height
            )
            layout.layoutParams = layoutParams
            layout.orientation = LinearLayout.HORIZONTAL
            layout.setBackgroundColor(ContextCompat.getColor(this,R.color.background))
            layout.addView( createText("${names[i]}: ",1))
            layout.addView(createText("Result: " + previousRating[i].toString() + "%->" + currentRating[i].toString()+"%",0))
            val image = ImageView(this)
            val imageParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            imageParams.setMargins(0,20,10,20)
            image.layoutParams = imageParams
            if (currentRating[i] > previousRating[i]){
                image.setImageResource(R.drawable.arrow_up_results)
            }else if(currentRating[i] < previousRating[i]){
                image.setImageResource(R.drawable.arrow_down_results)
            }
            layout.addView(image)

            val card = CardView(this)
            val cardParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                resources.getDimension(R.dimen.instrument_card_height).toInt()
            )
            cardParams.setMargins(resources.getDimension(R.dimen.base_margin).toInt(), resources.getDimension(R.dimen.base_margin).toInt(), 0, 0)
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
            resources.getDimension(R.dimen.result_text_card_width).toInt(), // CardView width
            LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
        )
        textParams.setMargins(resources.getDimension(R.dimen.big_margin).toInt(), 0, 0, 0)
        if (counter == 1){
            textParams = LinearLayout.LayoutParams(
                resources.getDimension(R.dimen.instrumentCard_text_width).toInt(), // CardView width
                LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
            )
            textParams.setMargins(resources.getDimension(R.dimen.base_margin).toInt(), 0, 0, 0)
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