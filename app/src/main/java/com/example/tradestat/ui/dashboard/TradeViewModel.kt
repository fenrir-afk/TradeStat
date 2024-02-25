package com.example.tradestat.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.Trade
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.Strategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TradeViewModel(application: Application) : AndroidViewModel(application) {
    val getTradesList:LiveData<List<Trade>>
    var getStrategyList:List<Strategy> = arrayListOf()
    var getInstrumentList:List<Instrument> = arrayListOf()
    var sortedTradeList:List<Trade> = arrayListOf()
    private val repository:TradesRepository
    init {
        val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
        val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
        val instrumentDao = TradeDatabase.getDatabase(application).getInstrumentDao()
        repository = TradesRepository(tradeDao,strategyDao,instrumentDao)
        getTradesList = repository.readAllData
    }
    //trade section
    fun addTrade(trade: Trade){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTrade(trade)
        }
    }
    fun deleteTrade(trade: Trade){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTrade(trade)
        }
    }
    suspend fun updateListByDate(){ //in this method we get actual trade list sorted by date
        var job = viewModelScope.async(Dispatchers.IO) {
            sortedTradeList = repository.getSortedByDateList()
        }
        job.await()
    }
    suspend fun updateListByStrategy(strategy: String){ //in this method we get actual trade list sorted by strategy
        var job = viewModelScope.async(Dispatchers.IO) {
            sortedTradeList = repository.getSortedByStrategList(strategy)
        }
        job.await()
    }
    suspend fun updateListByInstrument(instrument: String){ //in this method we get actual trade list sorted by instument
        var job = viewModelScope.async(Dispatchers.IO) {
            sortedTradeList = repository.getSortedByInstrumenList(instrument)
        }
        job.await()
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