package com.example.tradestat.ui.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.NoteCard
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(rep: BaseRepository): ViewModel(){
    private val repository: BaseRepository = rep
    val notes: MutableLiveData<List<NoteCard>> = MutableLiveData()
    fun addNote(noteCard: NoteCard){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(noteCard)
            notes.postValue(repository.getAllNotes())
        }
    }
    fun getAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            notes.postValue(repository.getAllNotes())
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