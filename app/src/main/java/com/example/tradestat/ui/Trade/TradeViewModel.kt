package com.example.tradestat.ui.Trade

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
            sortedTradeList.postValue(repository.getTradesSortedByDateAscending())//Update list
        }
    }
    fun deleteTrade(trade: Trade){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTrade(trade)
            var instrumentCounter =0
            var strategyCounter =0
            repository.getTradesSortedByDateDescending().forEach{
                if (it.strategy == trade.strategy){
                    strategyCounter++
                }
                if (it.instrument == trade.instrument){
                    instrumentCounter++
                }
            }
            if (strategyCounter == 0){
                repository.deleteStrategy(trade.strategy)
            }
            if (instrumentCounter == 0){
                repository.deleteInstrument(trade.instrument)
            }
            sortedTradeList.postValue(repository.getTradesSortedByDateAscending())//Update list
        }
    }

    fun updateListByDateDescending(){ //in this method we get actual trade list sorted by date
        viewModelScope.launch(Dispatchers.IO) {
            getTradesList.postValue(repository.getTradesSortedByDateDescending())
        }
    }
    fun updateListByDateAscending(){ //in this method we get actual trade list sorted by date
        viewModelScope.launch(Dispatchers.IO) {
            sortedTradeList.postValue(repository.getTradesSortedByDateAscending())
        }
    }
    fun updateListByStrategy(strategy: String){ //in this method we get actual trade list sorted by strategy
      viewModelScope.launch(Dispatchers.IO) {
            sortedTradeList.postValue(repository.getSortedByStrategiesList(strategy))
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
            getStrategyList = repository.getAllStrategies()
        }
    }
    fun addStrategy(strategy: Strategy){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStrategy(strategy)
        }
    }

    //instrument section

    fun readInstrumentsFromRepository(){
        viewModelScope.launch(Dispatchers.IO) {
            getInstrumentList = repository.getAllInstruments()
        }
    }
    fun addInstrument(instrument: Instrument){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addInstrument(instrument)
        }
    }

}