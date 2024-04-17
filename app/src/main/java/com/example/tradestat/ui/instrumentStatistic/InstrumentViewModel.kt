package com.example.tradestat.ui.instrumentStatistic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import com.example.tradestat.data.model.Directions
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Trade
import com.example.tradestat.ui.RatingCounter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InstrumentViewModel(application: Application) : AndroidViewModel(application){
        val getWinRateList: MutableLiveData<List<Int> > = MutableLiveData()
        var getWinRateListLong: MutableList<Int> = mutableListOf()
        val getWinRateListShort: MutableLiveData<List<Int> > = MutableLiveData()
        var instrumentsNames: MutableList<String> = mutableListOf()
        private val repository: TradesRepository
        init {
            val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
            val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
            val instrumentDao = TradeDatabase.getDatabase(application).getInstrumentDao()
            repository = TradesRepository(tradeDao,strategyDao,instrumentDao)
            getData()
        }
    private fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            val instruments = repository.getInstrumentList()
            instruments.forEach{
                instrumentsNames.add(it.instrumentName)
            }
            val winTrades = repository.getTradesByResult(Results.Victory.name)
            val defeatTrades = repository.getTradesByResult(Results.Defeat.name)
            val ratingObj = RatingCounter(instrumentsNames,winTrades,defeatTrades,1)
            ratingObj.updateData()
            getWinRateListLong = ratingObj.longWinRateList
            getWinRateListShort.postValue(ratingObj.shortWinRateList)
            getWinRateList.postValue(ratingObj.winRateList)//общий

        }
    }

}