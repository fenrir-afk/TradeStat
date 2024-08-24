package com.example.presentation.ui.notes
import android.app.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.domain.model.NoteCard
import com.example.presentation.databinding.ActivityNoteBinding
import com.example.presentation.utils.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private  val noteViewModel: NoteViewModel by viewModels()
    private val adapter = NoteAdapter(this)
    private var selectedPosition: Int = -1
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        setSupportActionBar(binding.include.myToolBar)

        val recyclerView: RecyclerView = findViewById(R.id.noteList)
        noteViewModel.getAllNotes()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        binding.addNoteFab.setOnClickListener{
            val note = NoteCard(0, mutableListOf(""), mutableListOf(),"Title")
            noteViewModel.addNote(note)
        }
        val pickImageLauncher = setImageLauncher()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                noteViewModel.notesFlow.filter { it.isNotEmpty() }.collect{
                    adapter.setData(it)
                    recyclerView.adapter = adapter
                }
            }
        }
        adapter.onImageClickListener = object : NoteAdapter.OnImageClickListener {
            override fun onImageClick(position: Int) {
                selectedPosition = position
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                pickImageLauncher.launch(galleryIntent)
            }
        }
    }
    /**
     * In this method we  turn on onActivity result method to get the images from gallery
     * */
    private fun setImageLauncher(): ActivityResultLauncher<Intent> {
        return ImageHelper(this@NoteActivity).pickImage(filesDir){savedImagePath ->
            adapter.updateImage(selectedPosition, savedImagePath)
            noteViewModel.updateNote(adapter.noteList[selectedPosition])
        }
    }

}