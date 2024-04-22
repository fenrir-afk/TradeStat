package com.example.tradestat.ui.strategyStatistic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import com.example.tradestat.data.model.DaysOfWeek
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Trade
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StrategyViewModel(application: Application) : AndroidViewModel(application) {
    val  strategiesNames: MutableList<String> = mutableListOf()
    val  entriesList: MutableLiveData<MutableList<List<Entry>>> =  MutableLiveData()

    private val repository: TradesRepository
    init {
        val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
        val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
        val instrumentDao = TradeDatabase.getDatabase(application).getInstrumentDao()
        repository = TradesRepository(tradeDao,strategyDao,instrumentDao)
        updateDay()
    }
    private fun updateDay(){ // in this method we get coordinates relatively to the trade list
        viewModelScope.launch(Dispatchers.IO) {
            val strategies = repository.getAllStrategies()
            val trades =repository.getSortedByDateDescending()
            val strategyLists = mutableListOf<List<Entry>>()
            for (i in strategies.indices){
                strategiesNames.add(strategies[i].strategyName)
                var entryList = mutableListOf<Entry>()
                var counter1 = 0f
                var counter2 = 0f
                for (b in trades.indices){
                    if (trades[b].strategy == strategies[i].strategyName){
                        counter1++
                        if (trades[b].tradeResult == Results.Victory.name){
                            counter2++
                        }else{
                            counter2--
                        }
                        entryList.add(Entry(counter1, counter2))
                    }
                }
                strategyLists.add(entryList)
            }
            entriesList.postValue(strategyLists)
            println("Hello")
        }

    }
}