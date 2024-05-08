package com.example.tradestat.ui.notes

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.tradestat.R
import com.example.tradestat.data.model.Trade

class NoteAdapter(private val viewModelStoreOwner: ViewModelStoreOwner)
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var titleList: List<String> = listOf()
    private val expandedPositions = mutableSetOf<Int>()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val childLayout = itemView.findViewById<EditText>(R.id.title)!!
        val parentLayout = itemView.findViewById<LinearLayout>(R.id.parent_layout)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_card,
            parent,
            false
        )
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = titleList[position]
        val context = holder.itemView.context
    }
    private fun isDarkTheme(context: Context): Boolean {
        val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
    }

    fun setData(titles: List<String>) {
        titleList = titles
        notifyDataSetChanged()
    }
    fun getTrade(position: Int): String {
        return titleList[position]
    }

    override fun getItemCount(): Int = titleList.size
}