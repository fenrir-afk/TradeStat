package com.example.tradestat.ui.notes

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tradestat.R
import com.example.tradestat.data.model.NoteCard
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteAdapter(private val viewModelStoreOwner: ViewModelStoreOwner)
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

     var noteList: MutableList<NoteCard> = mutableListOf()
    var onImageClickListener: OnImageClickListener? = null

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<EditText>(R.id.title)!!
        val parentLayout = itemView.findViewById<LinearLayout>(R.id.parent_layout)!!
        val imageButton = itemView.findViewById<FloatingActionButton>(R.id.add_image_Fab)!!
        val text1 = itemView.findViewById<EditText>(R.id.edit_text)!!
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
        val context = holder.itemView.context
        val noteViewModel = ViewModelProvider(viewModelStoreOwner)[NoteViewModel::class.java]
        holder.text1.setText(noteList[position].noteTexts[0])
        holder.title.setText(noteList[position].title)
        holder.title.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Неиспользуемый метод
            }
            override fun afterTextChanged(s: Editable?) {
                val enteredText = s.toString()
                if (enteredText.isNotEmpty()) {
                    val note =  noteList[position]
                    var noteTexts = note.noteTexts.toMutableList()
                    noteList[position] = NoteCard(noteList[position].id,noteTexts,noteList[position].noteImages,enteredText)
                    noteViewModel.updateNote(NoteCard(noteList[position].id,noteTexts,noteList[position].noteImages,enteredText))
                }
            }
        })
        holder.text1.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Неиспользуемый метод
            }
            override fun afterTextChanged(s: Editable?) {
                val enteredText = s.toString()
                if (enteredText.isNotEmpty()) {
                    val note =  noteList[position]
                    var noteTexts = note.noteTexts.toMutableList()
                    noteTexts[0] = enteredText
                    noteList[position] = NoteCard(noteList[position].id,noteTexts,noteList[position].noteImages,noteList[position].title)
                    noteViewModel.updateNote(NoteCard(noteList[position].id,noteTexts,noteList[position].noteImages,noteList[position].title))
                }
            }
        })
        holder.imageButton.setOnClickListener{
            onImageClickListener?.onImageClick(position)
        }
        noteList[position].noteTexts.forEachIndexed { index, s ->
            var image = createImage(position,index,context)
            if (index == noteList[position].noteTexts.size -1){ //in this condition we fix the bag of 2 edit text at the bottom of the card
                return@forEachIndexed
            }
            if (noteList[position].noteTexts.size > 1){
                val editText = createText(context,position,index,noteViewModel)
                if (image != null){
                    holder.parentLayout.addView(image)
                }
                holder.parentLayout.addView(editText)
            }
        }
    }
    private fun  createText(context: Context,position: Int,index:Int,noteViewModel:NoteViewModel): EditText {
        val editText = EditText(context)
        val textParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, // Image width
            LinearLayout.LayoutParams.WRAP_CONTENT // Image height
        )
        editText.textSize = context.resources.getDimension(R.dimen.note_text)
        editText.layoutParams = textParams
        editText.setText(noteList[position].noteTexts[index+1])
        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Неиспользуемый метод
            }
            override fun afterTextChanged(s: Editable?) {
                val enteredText = s.toString()
                if (enteredText.isNotEmpty()) {
                    val note =  noteList[position]
                    var noteTexts = note.noteTexts.toMutableList()
                    noteTexts[index+1] = enteredText
                    noteList[position] = NoteCard(noteList[position].id,noteTexts,noteList[position].noteImages,noteList[position].title)
                    noteViewModel.updateNote(noteList[position])
                }
            }
        })
        return editText
    }
    private fun createImage(position: Int, counter: Int, context: Context): ImageView? {
        if (noteList[position].noteImages.isNotEmpty() and (counter < noteList[position].noteTexts.size-1)){
            val image = ImageView(context)
            val imageParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // Image width
                500 // Image height
            )
            image.layoutParams = imageParams
            Glide.with(context)
                .load(noteList[position].noteImages[counter])
                .into(image)
            return image
        }else{
            return null
        }
    }
    fun updateImage(position: Int, imageUri: String) {
        val note = noteList[position]
        val updatedNoteImages = note.noteImages.toMutableList() // Создаем изменяемую копию списка изображений
        if (noteList[position].noteImages[0] == ""){
            updatedNoteImages[0] = imageUri
        }else{
            updatedNoteImages.add(imageUri)
        }
        val updatedNoteTexts = note.noteTexts.toMutableList()  // Создаем изменяемую копию списка текстов
        updatedNoteTexts.add("")

        noteList[position] = NoteCard(note.id, updatedNoteTexts, updatedNoteImages,noteList[position].title)
        notifyItemChanged(position)
    }

    fun setData(noteCards: List<NoteCard>) {
        noteList = noteCards as ArrayList<NoteCard>
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = noteList.size
    interface OnImageClickListener {
        fun onImageClick(position: Int)
    }
}