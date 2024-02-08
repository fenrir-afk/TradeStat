package com.example.tradestat.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.Trade
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import com.example.tradestat.data.model.Strategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradeViewModel(application: Application) : AndroidViewModel(application) {
    val getTradesList:LiveData<List<Trade>>
    lateinit var getStrategyList:List<Strategy>
    private val repository:TradesRepository
    init {
        val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
        val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
        repository = TradesRepository(tradeDao,strategyDao)
        getTradesList = repository.readAllData
    }
    fun redStrategiesFromRepository(){
        viewModelScope.launch(Dispatchers.IO) {
            getStrategyList = repository.readStrategies()
        }
    }
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

}