package com.example.tradestat.ui.dashboard

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tradestat.R
import com.example.tradestat.data.model.Trade
import com.example.tradestat.databinding.FragmentTradeBinding

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
        val adapter = TradeAdapter()
        val manager = LinearLayoutManager(this.context)

        tradeViewModel.getTradesList.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            binding.recyclerView.layoutManager = manager // Назначение LayoutManager для RecyclerView
            binding.recyclerView.adapter = adapter
        })

        binding.fab.setOnClickListener{
           showDialog()
        }

        return root
    }
    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.decorView?.setBackgroundResource(R.drawable.dialog_background)
        dialog.setCancelable(true)
        val lp = dialog.window!!.attributes
        dialog.window!!.setDimAmount(0.7f);
        dialog.setContentView(R.layout.customdialog)
        dialog.show()
        dialog.findViewById<Button>(R.id.cancel_button).setOnClickListener{
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.add_button).setOnClickListener{
            dialog.dismiss()
            insertDaraToDb(dialog)
        }
    }
    fun insertDaraToDb(dialog:Dialog){
        var direction = dialog.findViewById<Spinner>(R.id.directionSpinner).selectedItem.toString()
        var date = dialog.findViewById<Spinner>(R.id.daysSpinner).selectedItem.toString()
        var strategy = dialog.findViewById<EditText>(R.id.strategy).text.toString()
        var result = dialog.findViewById<Spinner>(R.id.resultSpinner).selectedItem.toString()
        var instrument = dialog.findViewById<EditText>(R.id.instrument).text.toString()
        if (direction.isEmpty() || date.isEmpty() || strategy.isEmpty() || result.isEmpty() || instrument.isEmpty()){
            Log.e("DialogError","Some data is not written")
        }else{
            val trade = Trade(0, direction, date, strategy, result, instrument)
            tradeViewModel.addTrade(trade)
            Toast.makeText(this.context,"Added",Toast.LENGTH_LONG).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}