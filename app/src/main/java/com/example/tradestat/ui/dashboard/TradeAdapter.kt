package com.example.tradestat.ui.dashboard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tradestat.R
import com.example.tradestat.data.model.Trade


class TradeAdapter: RecyclerView.Adapter<TradeAdapter.TradeViewHolder>() {
    private lateinit var  tradeList: List<Trade>
    class TradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val background: ImageView = itemView.findViewById(R.id.backgroundColor)
        val strategyName: TextView = itemView.findViewById(R.id.strategy)
        val TradeResult: TextView = itemView.findViewById(R.id.result)
        val InstrumentName: TextView = itemView.findViewById(R.id.instrument)
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
        if (position.mod(2) == 1){
            holder.background.setBackgroundColor(Color.BLACK)
        }
        if (tradeList[position].tradeResult == "Victory"){
            holder.TradeResult.setTextColor(Color.GREEN)
        }else {
            holder.TradeResult.setTextColor(Color.RED)
        }
        holder.strategyName.text = tradeList[position].strategy
        holder.TradeResult.text = tradeList[position].tradeResult
        holder.InstrumentName.text = tradeList[position].instrument
        if (tradeList[position].tradeDirection == "Short"){
            holder.img.setImageResource(R.drawable.bear)
        }else{
            holder.img.setImageResource(R.drawable.mull)
        }
    }
    fun setData(trades:List<Trade>){
        tradeList = trades
        notifyDataSetChanged()
    }

    override fun getItemCount() = tradeList.size
}