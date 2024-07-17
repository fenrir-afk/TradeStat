package com.example.tradestat.ui.notes
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tradestat.R
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.data.model.NoteCard
import com.example.tradestat.databinding.ActivityNoteBinding
import com.example.tradestat.repository.TradesRepository
import com.example.tradestat.utils.BaseViewModelFactory
import java.io.File
import java.io.FileOutputStream

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private  val noteViewModel: NoteViewModel by viewModels {
        val repository = TradesRepository(TradeDatabase.getDatabase(this))
        BaseViewModelFactory(repository, Application())
    }
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

        val recyclerView: RecyclerView = findViewById(R.id.noteList)
        noteViewModel.getAllNotes()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        binding.addNoteFab.setOnClickListener{
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
    /**
    * In this method we aare getting image from the gallery and push it to the db and adapter
    * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            val imageName = "note_image_${System.currentTimeMillis()}.jpg" // Сгенерируйте уникальное имя файла
            val savedImagePath = saveImageToDevice(imageUri!!, imageName) // Сохраните изображение на устройстве

            adapter.updateImage(selectedPosition, savedImagePath)
            noteViewModel.updateNote(adapter.noteList[selectedPosition])
        }
    }

    private fun saveImageToDevice(imageUri: Uri, imageName: String): String {
        // Сохраните изображение на устройство и верните путь к файлу
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