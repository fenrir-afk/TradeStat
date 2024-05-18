package com.example.tradestat.ui.trade

import android.app.Application
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tradestat.R
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.model.DaysOfWeek
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade
import com.example.tradestat.databinding.FragmentTradeBinding
import com.example.tradestat.repository.TradesRepository
import java.text.SimpleDateFormat
import java.util.Date


class TradeFragment : Fragment() {
    companion object CardSettings {
        const val CHAR_NUMBER_IN_ROW = 25
        const val ITEMS_IN_ROW = 4
    }
    private var _binding: FragmentTradeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var tradeViewModel:TradeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTradeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val repository = TradesRepository(TradeDatabase.getDatabase(requireContext()))
        val viewModelProvideFactory = TradeViewModelFactory(Application(),repository)
        tradeViewModel = ViewModelProvider(this,viewModelProvideFactory)[TradeViewModel::class.java]
        tradeViewModel.updateListByDateAscending()
        val adapter = TradeAdapter(this)
        val manager = LinearLayoutManager(this.context)


        tradeViewModel.finalList.observe(viewLifecycleOwner) {
            adapter.setTradesData(it)
            binding.recyclerView.layoutManager = manager // Assigning LayoutManager to RecyclerView
            binding.recyclerView.adapter = adapter
        }

        binding.addTradeFab.setOnClickListener{
            tradeDialog()
        }
        binding.DateCard.setOnClickListener{
            if (binding.dateArrow.drawable.constantState == resources.getDrawable(R.drawable.arrow).constantState){
                tradeViewModel.updateListByDateDescending() // wait until we get the data and update UI
                binding.dateArrow.setImageResource(R.drawable.arrow_up)
            }else{
                binding.dateArrow.setImageResource(R.drawable.arrow)
                tradeViewModel.updateListByDateAscending()
            }
        }

        binding.instrumentCard.setOnClickListener{
            binding.instrumentArrow.setImageResource(R.drawable.arrow_up)
            tradeViewModel.readInstrumentsFromRepository()//updating the instrument data in the viewmodel
            instrumentDialog()
        }
        binding.StrategyCard.setOnClickListener{
            binding.strategyArrow.setImageResource(R.drawable.arrow_up)
            tradeViewModel.redStrategiesFromRepository()
            strategyDialog()
        }

        return root

    }
    /**
     * In this method, we implement the strategy dialog and its position
     * */
    private fun strategyDialog() {
        val dialog = Dialog(requireContext())
        with(dialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setDimAmount(0F)
            window?.decorView?.setBackgroundResource(R.drawable.dialog_background2)
            setCancelable(true)
            setContentView(R.layout.strategy_dialog)
        }
        val window: Window = dialog.window!!
        window.attributes = setTheStrategyDialogPosition(dialog)

        val arr: List<Strategy> = tradeViewModel.getStrategyList
        val parentLayout = dialog.findViewById<LinearLayout>(R.id.parent_layout)
        parentLayout.id =  R.id.strategy_sort_layout_id
        if (arr.isNotEmpty()){
            for (strategy in arr) {
                val text = createBaseStrategyItem(strategy.strategyName,dialog)
                parentLayout.addView(text)
            }
            dialog.setOnDismissListener {
                binding.strategyArrow.setImageResource(R.drawable.arrow)
            }
            dialog.show()
        }else{
            binding.strategyArrow.setImageResource(R.drawable.arrow)
            Toast.makeText(requireContext(),"List is empty",Toast.LENGTH_LONG).show()
            return
        }

    }
    /**
     * In this method, we create item for strategies table (Sorting table)
     * @param strategy is the name of the  strategy
     * @param dialog is dialog window
     * */
    private fun createBaseStrategyItem(strategy: String, dialog: Dialog): TextView {
        val textSizeInPx = resources.getDimensionPixelSize(R.dimen.dialog_text_size)
        val text = TextView(context)
        text.text = strategy
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeInPx.toFloat())
        text.setTextColor(ContextCompat.getColor(requireContext(), R.color.MorelightGray))
        text.gravity = Gravity.CENTER_VERTICAL
        text.setOnClickListener {
            updateRecyclerVIew(it,1)
            dialog.dismiss()
        }
        return text
    }
    /**
     *In this method, we update RecyclerVIew list with data from dialogs (strategy and instument)
     * @param i is an int value for changing  strategy dialog and  instrument dialog
     * */
    private fun updateRecyclerVIew(view: View, i: Int) {
        if (i == 1){ // code of strategyDialog
            val text =  (view as TextView).text.toString()
            tradeViewModel.updateListByStrategy(text)
        }else{ //instrument dialog
            val text =  (view as TextView).text.toString()
            tradeViewModel.updateListByInstrument(text)
        }
    }
    /**
     *In this method, we set the adaptive position for strategies dialog
     * */
    private fun setTheStrategyDialogPosition(dialog: Dialog): WindowManager.LayoutParams {
        //in this place we set the strategy dialog characteristics
        val window: Window = dialog.window!!
        val wlp: WindowManager.LayoutParams = window.attributes
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val dialogWidth = (screenWidth * 0.5).toInt()
        val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
        wlp.width = dialogWidth
        wlp.height = dialogHeight
        wlp.gravity = Gravity.END or Gravity.TOP
        val xMarginInPx = resources.getDimensionPixelSize(R.dimen.dialog_horizontal_margin)
        val yMarginInPx = resources.getDimensionPixelSize(R.dimen.dialog_vertical_margin)
        wlp.x = xMarginInPx
        wlp.y = yMarginInPx
        return wlp
    }
    /**
     *In this method, we dynamically create card representations representing instruments
     * */
    private fun instrumentDialog() {
        val dialog = Dialog(requireContext())
        with(dialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setDimAmount(0F)
            window?.decorView?.setBackgroundResource(R.drawable.dialog_background2)
            setCancelable(true)
            setContentView(R.layout.sort_by_tag)
        }

        val layout = dialog.findViewById<LinearLayout>(R.id.tag_section)
        val arr: List<Instrument> = tradeViewModel.getInstrumentList

        if (arr.isNotEmpty()) {
            layout.addView(createCardsForList(arr,dialog))

            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

            val marginFromRight = convertDpToPixels(10f) // Convert dp to pixels
            val marginFromTop = convertDpToPixels(115f) // Convert dp to pixels

            val window: Window = dialog.window!!
            val lp: WindowManager.LayoutParams = window.attributes
            lp.gravity = Gravity.TOP or Gravity.END
            lp.x = marginFromRight
            lp.y = marginFromTop
            window.attributes = lp

            dialog.show()
        } else {
            binding.instrumentArrow.setImageResource(R.drawable.arrow)
            Toast.makeText(requireContext(), "List is empty", Toast.LENGTH_LONG).show()
            return
        }

        dialog.setOnDismissListener {
            binding.instrumentArrow.setImageResource(R.drawable.arrow)
        }
    }

    private fun convertDpToPixels(dp: Float): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
    /**
     *In this method, we create cards for strategy dialog
     * */
    private fun createCardsForList(arr: List<Instrument>, dialog: Dialog): LinearLayout {
        var counter = 0 // count the index of current strategy
        var globalCharNumber = 0
        val globalLayout = LinearLayout(context) //layout that contain rows
        globalLayout.orientation = LinearLayout.VERTICAL
        for (i in 0..arr.size){
            globalCharNumber += arr.size
        }

        val rowNumber = globalCharNumber / 29
        for (row in 0..rowNumber) {
            val rowLayout = LinearLayout(context)
            rowLayout.orientation = LinearLayout.HORIZONTAL
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // CardView width
                LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
            )
            rowLayout.layoutParams = layoutParams

            var charCounter = 0 // count the number of characters in a single row
            while (charCounter + arr[counter].instrumentName.length <= CHAR_NUMBER_IN_ROW) { //check the number of chars in a row
                charCounter += arr[counter].instrumentName.length

                val cardView = createBaseInstrumentCard(arr[counter].instrumentName,dialog) //create card with textView and CLickListener

                rowLayout.addView(cardView)
                counter++ // counter counts the number of created cardViews

                if (counter == arr.size) {
                    globalLayout.addView(rowLayout)
                    return globalLayout
                }

                //Here is the setting the number of items in a row
                if (counter % ITEMS_IN_ROW == 0) {
                    break
                }
            }
            globalLayout.addView(rowLayout)
        }
        return globalLayout
    }
    /**
     *In this method, we create a base card for Instrument Dialog
     * */
    private fun createBaseInstrumentCard(instrumentName: String, dialog: Dialog): CardView {
        val cardView = CardView(requireContext())
        val cardParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, // CardView width
            LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
        )
        cardParams.setMargins(20, 18, 0, 0)
        cardView.layoutParams = cardParams
        cardView.radius = 16F
        cardView.setCardBackgroundColor(Color.parseColor("#4B836B6B"))
        cardView.setContentPadding(10, 10, 10, 10)
        val textView = TextView(context)
        textView.setTextColor(Color.parseColor("#94FFFFFF"))
        textView.text = instrumentName

        textView.setOnClickListener {//implementation of list sorting
           updateRecyclerVIew(it,2) //in this method we update the recyclerVIew list, 2 is the instrument dialog code
            dialog.dismiss()
        }

        cardView.addView(textView)
        return cardView
    }
    /**
     * In this method, we create and display dialog for adding trade to the main trade list
     * */
    private fun tradeDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.decorView?.setBackgroundResource(R.drawable.dialog_background)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.customdialog)
        dialog.show()
        dialog.findViewById<Button>(R.id.cancel_button).setOnClickListener{
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.add_button).setOnClickListener{
            dialog.dismiss()
            insertDataToDb(dialog)
        }
    }
    /**
     * In this method, we insert data to data base
     * */
    private fun insertDataToDb(dialog:Dialog){
        val direction = dialog.findViewById<Spinner>(R.id.directionSpinner).selectedItem.toString()

        var date = dialog.findViewById<Spinner>(R.id.daysSpinner).selectedItem.toString()
        when (date) {
            "Воскресенье" -> date = DaysOfWeek.Sunday.name
            "Понедельник" -> date = DaysOfWeek.Monday.name
            "Вторник" -> date = DaysOfWeek.Thursday.name
            "Среда" -> date = DaysOfWeek.Wednesday.name
            "Четверг" -> date = DaysOfWeek.Thursday.name
            "Пятница" -> date = DaysOfWeek.Friday.name
            "Суббота" -> date = DaysOfWeek.Saturday.name
        }
        val strategy = dialog.findViewById<EditText>(R.id.strategy_field).text.toString()

        var result = dialog.findViewById<Spinner>(R.id.resultSpinner).selectedItem.toString()
        if (result == "Победа"){
            result = Results.Victory.name
        }else if(result == "Поражение"){
            result = Results.Defeat.name
        }

        val instrument = dialog.findViewById<EditText>(R.id.instrument_field).text.toString()
        val description = dialog.findViewById<EditText>(R.id.description).text.toString()
        if (direction == "Trade direction:" || date == "Day of the week:" || strategy.isEmpty() || result=="Trade result:" || instrument.isEmpty()){
            Toast.makeText(this.context,"Some fields was not written",Toast.LENGTH_LONG).show()

        }else{
            //getting current date
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val currentDate = sdf.format(Date())
            val trade = Trade(0, direction, date, strategy, result, instrument,currentDate,description)
            updateDb(trade,strategy,instrument)
            Toast.makeText(this.context,"Added",Toast.LENGTH_SHORT).show()
        }


    }
    private fun updateDb(trade: Trade, strategy: String, instrument: String){
        tradeViewModel.addInstrument(Instrument(0,instrument))
        tradeViewModel.addStrategy(Strategy(0,strategy))
        tradeViewModel.addTrade(trade)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}