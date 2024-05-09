package com.example.tradestat.ui.notes
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tradestat.R
import com.example.tradestat.data.model.NoteCard
import com.example.tradestat.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private val adapter = NoteAdapter(this)
    private var selectedPosition: Int = -1
    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        setSupportActionBar(binding.include.myToolBar)
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        noteViewModel.getAllNotes()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        binding.fab.setOnClickListener{
            val note = NoteCard(0, mutableListOf(""), mutableListOf(),"Title")
            noteViewModel.addNote(note)
        }
        noteViewModel.notes.observe(this){
            adapter.setData(it)
            recyclerView.adapter = adapter
        }
        adapter.onImageClickListener = object : NoteAdapter.OnImageClickListener {
            override fun onImageClick(position: Int) {
                selectedPosition = position
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            adapter.updateImage(selectedPosition, imageUri.toString())
            noteViewModel.updateNote(adapter.noteList[selectedPosition])
        }
    }


}