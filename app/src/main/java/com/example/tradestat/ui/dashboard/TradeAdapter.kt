package com.example.tradestat.ui.dashboard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.tradestat.R
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TradeAdapter(private val viewModelStoreOwner: ViewModelStoreOwner)
    : RecyclerView.Adapter<TradeAdapter.TradeViewHolder>() {

    private var tradeList: List<Trade> = listOf()
    private val expandedPositions = mutableSetOf<Int>()

    class TradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val childLayout = itemView.findViewById<LinearLayout>(R.id.child_layout)
        val background: ImageView = itemView.findViewById(R.id.background)
        val parentLayout: LinearLayout = itemView.findViewById(R.id.parent_layout)
        val strategyName: TextView = itemView.findViewById(R.id.strategy)
        val tradeResult: TextView = itemView.findViewById(R.id.result)
        val instrumentName: TextView = itemView.findViewById(R.id.instrument)
        val date: TextView = itemView.findViewById(R.id.Date)
        val addDate: TextView = itemView.findViewById(R.id.AddDate)
        val description: TextView = itemView.findViewById(R.id.Description)
        val deleteButton: FloatingActionButton = itemView.findViewById(R.id.deleteFab)
        val image: ImageView = itemView.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent,
            false
        )
        return TradeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TradeViewHolder, position: Int) {
        val trade = tradeList[position]


        // Set color according to trade result
        holder.tradeResult.setTextColor(
            if (trade.tradeResult == "Victory") Color.GREEN
            else Color.RED
        )

        // Set trade information
        with(holder) {
            strategyName.text = trade.strategy
            tradeResult.text = trade.tradeResult
            instrumentName.text = trade.instrument
            date.text = "Date: ${trade.tradeDate}"
            addDate.text = "Date of adding: ${trade.ADDate}"
            description.text = "Description: ${trade.description}"

            // Delete trade
            deleteButton.setOnClickListener {
                val tradeViewModel = ViewModelProvider(viewModelStoreOwner).get(TradeViewModel::class.java)
                tradeViewModel.deleteTrade(trade)
                notifyItemRemoved(position)
            }

            // Set image resource according to trade direction
            image.setImageResource(
                if (trade.tradeDirection == "Short") R.drawable.bear
                else R.drawable.mull
            )

            // Set background color
            background.setBackgroundResource(
                if (position % 2 == 1) R.color.black
                else R.color.background
            )
        }

        // Implementation of extensibility of recyclerView item
        val isExpanded = expandedPositions.contains(position)
        holder.childLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE

        holder.parentLayout.setOnClickListener {
            if (isExpanded) expandedPositions.remove(position)
            else expandedPositions.add(position)
            notifyItemChanged(position) // Only notify the changed item
        }
    }

    fun setTradesData(trades: List<Trade>) {
        tradeList = trades
        notifyDataSetChanged()
    }
    fun getTrade(position: Int): Trade {
        return tradeList[position]
    }

    override fun getItemCount(): Int = tradeList.size
}