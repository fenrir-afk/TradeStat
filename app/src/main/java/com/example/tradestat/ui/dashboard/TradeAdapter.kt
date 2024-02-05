package com.example.tradestat.ui.dashboard

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.tradestat.R
import com.example.tradestat.data.model.Trade
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TradeAdapter(private val context: ViewModelStoreOwner): RecyclerView.Adapter<TradeAdapter.TradeViewHolder>() {
    private lateinit var tradeList: List<Trade>
    private val expandedPositions = mutableSetOf<Int>()// Set to store the expanded positions

    class TradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val child_layout: LinearLayout = itemView.findViewById(R.id.child_layout)
        val background: ImageView = itemView.findViewById(R.id.background)
        val parent_layout: LinearLayout = itemView.findViewById(R.id.parent_layout)
        val strategyName: TextView = itemView.findViewById(R.id.strategy)
        val TradeResult: TextView = itemView.findViewById(R.id.result)
        val InstrumentName: TextView = itemView.findViewById(R.id.instrument)
        val Date: TextView = itemView.findViewById(R.id.Date)
        val AddDate: TextView = itemView.findViewById(R.id.AddDate)
        val description: TextView = itemView.findViewById(R.id.Description)
        val deleteButton: FloatingActionButton = itemView.findViewById(R.id.deleteFab)
        val img: ImageView = itemView.findViewById(R.id.img)
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
        //set color according to trade result
        if (tradeList[position].tradeResult == "Victory") {
            holder.TradeResult.setTextColor(Color.GREEN)
        } else {
            holder.TradeResult.setTextColor(Color.RED)
        }
        //
        holder.strategyName.text = tradeList[position].strategy
        holder.TradeResult.text = tradeList[position].tradeResult
        holder.InstrumentName.text = tradeList[position].instrument
        holder.Date.text = "Date: " + tradeList[position].tradeDate
        holder.AddDate.text = "Date of adding: " + tradeList[position].ADDate
        holder.description.text = "Description: " + tradeList[position].description
        holder.deleteButton.setOnClickListener {
            var tradeViewModel = ViewModelProvider(context)[TradeViewModel::class.java]
            tradeViewModel.deleteTrade(tradeList[position])
            notifyItemRemoved(position)
        }

        //set image resource according to trade direction
        if (tradeList[position].tradeDirection == "Short") {
            holder.img.setImageResource(R.drawable.bear)
        } else {
            holder.img.setImageResource(R.drawable.mull)
        }
        //set background color
        if (position.mod(2) == 1){
            holder.background.setBackgroundColor(Color.BLACK)
        } else {
            holder.background.setBackgroundResource(R.color.background)
        }
        //Implementation of extensibility of resyclerView item
        val isExpanded = expandedPositions.contains(position)
        holder.child_layout.visibility = if (isExpanded) View.VISIBLE else View.GONE

        holder.parent_layout.setOnClickListener {
            if (isExpanded) {
                expandedPositions.remove(position)
            } else {
                expandedPositions.add(position)
            }
            notifyItemChanged(position) // Only notify the changed item
        }
    }

    fun setData(trades: List<Trade>) {
        tradeList = trades
        notifyDataSetChanged()
    }
    fun getTrade(position: Int): Trade {
        return tradeList[position]
    }

    override fun getItemCount() = tradeList.size
}