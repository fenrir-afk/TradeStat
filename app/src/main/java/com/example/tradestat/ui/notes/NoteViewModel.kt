package com.example.tradestat.ui.notes


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.NoteCard
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(rep: BaseRepository): ViewModel(){
    private val repository: BaseRepository = rep
    private var _notesFlow = MutableStateFlow<List<NoteCard>>(emptyList())
    val notesFlow = _notesFlow.asStateFlow()

    fun addNote(noteCard: NoteCard){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(noteCard)
            _notesFlow.emit(repository.getAllNotes())
        }
    }
    fun getAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            _notesFlow.emit(repository.getAllNotes())
        }
    }
    fun updateNote(noteCard: NoteCard){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(noteCard)
        }
    }
    fun deleteNote(noteCard: NoteCard){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(noteCard)
        }
    }

}