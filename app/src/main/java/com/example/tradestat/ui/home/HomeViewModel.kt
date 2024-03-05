package com.example.tradestat.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val getNumberList:MutableLiveData<List<Int>> = MutableLiveData()
    var shortNumber: Int = 0
    var longNumber: Int = 0
    var winNumber: Int = 0
    var defeatNumber: Int = 0
    private val repository:TradesRepository

    init {
        val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
        val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
        val instrumentDao = TradeDatabase.getDatabase(application).getInstrumentDao()
        repository = TradesRepository(tradeDao,strategyDao,instrumentDao)
        updateNumbers()
    }

   fun updateNumbers(){
        viewModelScope.launch(Dispatchers.IO) {
            shortNumber = repository.getShortPos()
            longNumber = repository.getLongPos()
            winNumber = repository.getWinNumber()
            defeatNumber = repository.getDefNumber()
            getNumberList.postValue(listOf(shortNumber,longNumber,winNumber,defeatNumber))
        }
    }

}