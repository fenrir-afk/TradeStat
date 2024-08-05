package com.example.presentation.ui.notes

import android.app.Activity
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.NoteCard
import com.example.domain.note.usecase.AddNoteUseCase
import com.example.domain.note.usecase.DeleteNoteUseCase
import com.example.domain.note.usecase.GetAllNotesUseCase
import com.example.domain.note.usecase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private  val addNoteUseCase: AddNoteUseCase,
    private  val deleteNoteUseCase: DeleteNoteUseCase,
    private  val getAllNotesUseCase: GetAllNotesUseCase,
    private  val updateNoteUseCase: UpdateNoteUseCase
): ViewModel(){
    private var _notesFlow = MutableStateFlow<List<NoteCard>>(emptyList())
    val notesFlow = _notesFlow.asStateFlow()

    fun addNote(noteCard: NoteCard){
        viewModelScope.launch(Dispatchers.IO) {
            addNoteUseCase.execute(noteCard)
            _notesFlow.emit(getAllNotesUseCase.execute())
        }
    }
    fun getAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            _notesFlow.emit(getAllNotesUseCase.execute())
        }
    }
    fun updateNote(noteCard: NoteCard){
        viewModelScope.launch(Dispatchers.IO) {
           updateNoteUseCase.execute(noteCard)
        }
    }
    fun deleteNote(noteCard: NoteCard){
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase.execute(noteCard)
        }
    }
}