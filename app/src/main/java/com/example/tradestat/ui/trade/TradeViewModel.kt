package com.example.tradestat.ui.trade


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.Trade
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TradeViewModel(rep: BaseRepository) : ViewModel() {

    private var _sortedTradesListFlow = MutableStateFlow<List<Trade>>(emptyList())
    val sortedTradesListFlow = _sortedTradesListFlow.asStateFlow()

    var getStrategyList:List<Strategy> = arrayListOf()
    var getInstrumentList:List<Instrument> = arrayListOf()
    private val repository: BaseRepository = rep


    //trade section
    fun addTrade(trade: Trade){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTrade(trade)
            _sortedTradesListFlow.emit(repository.getTradesSortedByDateAscending())
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
            _sortedTradesListFlow.emit(repository.getTradesSortedByDateAscending())//update list
        }
    }
    /**
     * In this method we get actual trade list sorted by date
     * */
    fun updateListByDateDescending(){
        viewModelScope.launch(Dispatchers.IO) {
            _sortedTradesListFlow.emit(repository.getTradesSortedByDateDescending())
        }
    }
    /**
     * In this method we get actual trade list sorted by date
     * */
    fun updateListByDateAscending(){
        viewModelScope.launch(Dispatchers.IO) {
            _sortedTradesListFlow.emit(repository.getTradesSortedByDateAscending())
        }
    }
    /**
     * In this method we get actual trade list sorted by strategy
     * */
    fun updateListByStrategy(strategy: String){
      viewModelScope.launch(Dispatchers.IO) {
          _sortedTradesListFlow.emit(repository.getSortedByStrategiesList(strategy))
        }
    }
    /**
     * In this method we get actual trade list sorted by instrument
     * */
    fun updateListByInstrument(instrument: String){
        viewModelScope.launch(Dispatchers.IO) {
            _sortedTradesListFlow.emit(repository.getSortedByInstrumentList(instrument))
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