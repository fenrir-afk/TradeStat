package com.example.presentation.ui.results

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
import com.example.presentation.R
import com.example.presentation.databinding.ActivityResultsBinding
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class ResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding
    private  val resultsViewModel: ResultsViewModel by viewModels()
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
        return if(month != 0){
            monthNames[month - 1]
        }else{
            monthNames[11]
        }
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
        with(dataSet1){
            color = ContextCompat.getColor(this@ResultsActivity,R.color.MorelightGray)
            lineWidth = 2F
            valueTextColor = ContextCompat.getColor(this@ResultsActivity,R.color.MorelightGray)
            fillColor = ContextCompat.getColor(this@ResultsActivity,R.color.MorelightGray)
            valueTextSize = 14f
            setDrawFilled(true)
        }
        val arr2 = ArrayList<RadarEntry>()
        previousMonthStrategiesRating.forEach {
            arr2.add(RadarEntry(it.toFloat()))
        }
        val dataSet2 = RadarDataSet(arr2,previousMonth)
        with(dataSet2){
            color = ContextCompat.getColor(this@ResultsActivity,R.color.green)
            lineWidth = 2F
            valueTextColor = ContextCompat.getColor(this@ResultsActivity,R.color.green)
            fillColor = ContextCompat.getColor(this@ResultsActivity,R.color.green)
            valueTextSize = 14f
            setDrawFilled(true)
        }
        val radarData = RadarData()
        radarData.addDataSet(dataSet1)
        radarData.addDataSet(dataSet2)
        with(binding.chart){
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            xAxis.textColor = getColor(R.color.white)
            yAxis.axisMinimum = 0f
            yAxis.axisMaximum = 110f
            xAxis.axisMinimum = 0f
            xAxis.axisMaximum = 110f
            description.text = ""
            data = radarData
            legend.textColor = getColor(R.color.white)

            xAxis.setDrawAxisLine(false)
            xAxis.setDrawLabels(true)
            yAxis.setDrawAxisLine(false)
            yAxis.setDrawLabels(false)
            animateXY(1000,1400)
            invalidate()
        }
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