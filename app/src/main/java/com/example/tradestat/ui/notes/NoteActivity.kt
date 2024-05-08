package com.example.tradestat.ui.notes
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tradestat.R
import com.example.tradestat.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = NoteAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setData(arrayListOf("Title 1", "Title 2", "Tile 3"))
        recyclerView.adapter = adapter
    }
}