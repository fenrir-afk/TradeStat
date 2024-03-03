package com.example.tradestat.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.Trade
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.Strategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradeViewModel(application: Application) : AndroidViewModel(application) {

    private val getTradesList:MutableLiveData<List<Trade>> = MutableLiveData()
    private var sortedTradeList:MutableLiveData<List<Trade>> = MutableLiveData()
    var finalList:MediatorLiveData<List<Trade>> = MediatorLiveData()

    var getStrategyList:List<Strategy> = arrayListOf()
    var getInstrumentList:List<Instrument> = arrayListOf()
    private val repository:TradesRepository

    init {
        val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
        val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
        val instrumentDao = TradeDatabase.getDatabase(application).getInstrumentDao()
        repository = TradesRepository(tradeDao,strategyDao,instrumentDao)
        finalList.addSource(getTradesList){
            finalList.value = it
        }
        finalList.addSource(sortedTradeList){
            finalList.value = it
        }
    }
    //trade section
    fun addTrade(trade: Trade){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTrade(trade)
            sortedTradeList.postValue(repository.getSortedByDateAscending())
        }
    }
    fun deleteTrade(trade: Trade){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTrade(trade)
            sortedTradeList.postValue(repository.getSortedByDateAscending())
        }
    }

    fun updateListByDateDescending(){ //in this method we get actual trade list sorted by date
        viewModelScope.launch(Dispatchers.IO) {
            getTradesList.postValue(repository.getSortedByDateDescending())
        }
    }
    fun updateListByDateAscending(){ //in this method we get actual trade list sorted by date
        viewModelScope.launch(Dispatchers.IO) {
            sortedTradeList.postValue(repository.getSortedByDateAscending())
        }
    }
    fun updateListByStrategy(strategy: String){ //in this method we get actual trade list sorted by strategy
      viewModelScope.launch(Dispatchers.IO) {
            sortedTradeList.postValue(repository.getSortedByStrategList(strategy))
        }
    }
    fun updateListByInstrument(instrument: String){ //in this method we get actual trade list sorted by instument
        viewModelScope.launch(Dispatchers.IO) {
            sortedTradeList.postValue(repository.getSortedByInstrumenList(instrument))
        }
    }
    //strategies section

    fun redStrategiesFromRepository(){
        viewModelScope.launch(Dispatchers.IO) {
            getStrategyList = repository.readStrategies()
        }
    }
    fun addStrategy(strategy: Strategy){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStrategy(strategy)
        }
    }
    fun deleteStrategy(strategy: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteStrategy(strategy)
        }
    }

    //instrument section

    fun readInstrumentsFromRepository(){
        viewModelScope.launch(Dispatchers.IO) {
            getInstrumentList = repository.readInstruments()
        }
    }
    fun addInstrument(instrument: Instrument){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addInstrument(instrument)
        }
    }
    fun deleteInstrument(instrument: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteInstrument(instrument)
        }
    }

}