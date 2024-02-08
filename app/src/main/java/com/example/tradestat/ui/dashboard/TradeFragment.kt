package com.example.tradestat.ui.dashboard

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.marginStart
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tradestat.R
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade
import com.example.tradestat.databinding.FragmentTradeBinding
import java.text.SimpleDateFormat
import java.util.Date

class TradeFragment : Fragment() {

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
        tradeViewModel = ViewModelProvider(this)[TradeViewModel::class.java]
        //RecyclerView implementation
        val adapter = TradeAdapter(this)
        val manager = LinearLayoutManager(this.context)


        tradeViewModel.getTradesList.observe(viewLifecycleOwner, Observer {
            adapter.setTradesData(it)
            binding.recyclerView.layoutManager = manager // Assigning LayoutManager to RecyclerView
            binding.recyclerView.adapter = adapter
        })

        binding.fab.setOnClickListener{
           tradeDialog()
        }
        binding.StrategyCard.setOnClickListener{
            tradeViewModel.redStrategiesFromRepository()//updating the strategy data in the viewmodel
            strategyDialog()
        }

        return root
    }
    /*
         In this method, we dynamically create card representations representing strategies
    */
    private fun strategyDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.decorView?.setBackgroundResource(R.drawable.dialog_background)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.sort_by_tag)
        val layout = dialog.findViewById<LinearLayout>(R.id.tag_section)
        var arr:List<Strategy> = tradeViewModel.getStrategyList
        layout.addView(createCardsForList(arr))
        dialog.show()
    }
    private fun createCardsForList(arr:List<Strategy>): LinearLayout {
        var counter = -1
        var numberOfRows = if (arr.size <= 4) 1 else arr.size/4+1
        val globalLayout = LinearLayout(context)
        rowFor@ for (row in 0..numberOfRows) {
            val rowLayout = LinearLayout(context)
            //params for layout of tags
            rowLayout.orientation = LinearLayout.HORIZONTAL
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // CardView width
                LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
            )
            //
            rowLayout.layoutParams = layoutParams
            for (item in 0..3) {
                val cardView = CardView(requireContext())
                //params for item of tag layout
                val CardParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, // CardView width
                    LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
                )
                CardParams.setMargins(20,18,0,0)
                //
                cardView.layoutParams = CardParams
                cardView.radius = 16F
                cardView.setCardBackgroundColor(Color.parseColor("#4B836B6B"));
                cardView.setContentPadding(10, 10, 10, 10)
                val textView = TextView(context)
                textView.setTextColor(Color.parseColor("#94F6EFEF"))
                //counter count the number of already created cardVies
                counter++
                if(arr.size > counter){
                    textView.text = arr[counter].strategyName
                    cardView.addView(textView)
                    rowLayout.addView(cardView)
                }else{
                    break
                }
            }
            globalLayout.addView(rowLayout)
        }
        return  globalLayout
    }
    /*
         In this method, we create and display dialog for adding trade to the main trade list
    */
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
    /*
         In this method, we insert data to data base
    */
    private fun insertDataToDb(dialog:Dialog){
        var direction = dialog.findViewById<Spinner>(R.id.directionSpinner).selectedItem.toString()
        var date = dialog.findViewById<Spinner>(R.id.daysSpinner).selectedItem.toString()
        var strategy = dialog.findViewById<EditText>(R.id.strategy).text.toString()
        var result = dialog.findViewById<Spinner>(R.id.resultSpinner).selectedItem.toString()
        var instrument = dialog.findViewById<EditText>(R.id.instrument).text.toString()
        var description = dialog.findViewById<EditText>(R.id.description).text.toString()
        if (direction.isEmpty() || date.isEmpty() || strategy.isEmpty() || result.isEmpty() || instrument.isEmpty()){
            Toast.makeText(this.context,"Some fields was not written",Toast.LENGTH_LONG).show()

        }else{
            //getting current date
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            //
            val trade = Trade(0, direction, date, strategy, result, instrument,currentDate,description)
            tradeViewModel.addTrade(trade)
            tradeViewModel.addStrategy(Strategy(0,strategy))
            Toast.makeText(this.context,"Added",Toast.LENGTH_SHORT).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}