package com.example.tradestat.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import com.example.tradestat.data.model.NoteCard
import com.example.tradestat.data.model.Trade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application):AndroidViewModel(application) {
    private val repository: TradesRepository
    val notes: MutableLiveData<List<NoteCard>> = MutableLiveData()
    init {
        val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
        val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
        val instrumentDao = TradeDatabase.getDatabase(application).getInstrumentDao()
        val noteDao = TradeDatabase.getDatabase(application).getNoteDao()
        repository = TradesRepository(tradeDao,strategyDao,instrumentDao,noteDao)
    }
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