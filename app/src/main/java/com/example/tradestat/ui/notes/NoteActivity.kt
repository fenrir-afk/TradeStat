package com.example.tradestat.ui.notes
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
import com.example.tradestat.R
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.data.model.NoteCard
import com.example.tradestat.databinding.ActivityNoteBinding
import com.example.tradestat.repository.TradesRepository
import com.example.tradestat.utils.BaseViewModelFactory
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private  val noteViewModel: NoteViewModel by viewModels {
        val repository = TradesRepository(TradeDatabase.getDatabase(this))
        BaseViewModelFactory(repository)
    }
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
        pickImage()
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
    * In this method we aare getting image from the gallery and push it to the db and adapter
    * */
    private fun pickImage(){
        pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                if (imageUri != null) {
                    val imageName = "note_image_${System.currentTimeMillis()}.jpg"
                    val savedImagePath = saveImageToDevice(imageUri, imageName)

                    adapter.updateImage(selectedPosition, savedImagePath)
                    noteViewModel.updateNote(adapter.noteList[selectedPosition])
                }
            }
        }
    }


    private fun saveImageToDevice(imageUri: Uri, imageName: String): String {
        // Save the image to your device and return the file path
        val inputStream = contentResolver.openInputStream(imageUri)
        val file = File(filesDir, imageName)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.flush()
        outputStream.close()
        return file.absolutePath
    }


}