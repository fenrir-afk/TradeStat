package com.example.tradestat.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.Trade
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradeViewModel(application: Application) : AndroidViewModel(application) {
    val getTradesList:LiveData<List<Trade>>
    private val repository:TradesRepository
    init {
        val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
        repository = TradesRepository(tradeDao)
        getTradesList = repository.readAllData
    }
    fun addTrade(trade: Trade){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTrade(trade)
        }
    }

}