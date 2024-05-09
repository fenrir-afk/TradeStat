package com.example.tradestat.ui.strategyStatistic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import com.example.tradestat.data.model.Results
import com.example.tradestat.ui.RatingCounter
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StrategyViewModel(application: Application) : AndroidViewModel(application) {
    var getWinRateListLong: MutableList<Int> = mutableListOf()

    var tradeLongNumbers: MutableList<Int> = mutableListOf()
    var tradeShortNumbers: MutableList<Int> =  mutableListOf()

    var tradeNumbers: MutableList<Int> =  mutableListOf()

    val getWinRateListShort:MutableLiveData<MutableList<Int>> = MutableLiveData()

    var strategiesNames: MutableList<String> = mutableListOf()
    val entriesList: MutableLiveData<MutableList<List<Entry>>> =  MutableLiveData()

    private val repository: TradesRepository
    init {
        val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
        val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
        val instrumentDao = TradeDatabase.getDatabase(application).getInstrumentDao()
        val noteDao = TradeDatabase.getDatabase(application).getNoteDao()
        repository = TradesRepository(tradeDao,strategyDao,instrumentDao,noteDao)
    }
    /**
     * In this method we get coordinates relatively to the trade list
    * */
     fun updateData(){
        viewModelScope.launch(Dispatchers.IO) {
            strategiesNames = mutableListOf()
            val strategies = repository.getAllStrategies()
            val trades =repository.getTradesSortedByDateDescending()
            val strategyLists = mutableListOf<List<Entry>>()
            for (i in strategies.indices){
                strategiesNames.add(strategies[i].strategyName)
                val entryList = mutableListOf<Entry>()
                entryList.add(Entry(0f, 0f))
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
            updateRatingData(strategiesNames)
        }

    }
    /**
     * In this method we update rating data int the viewModel
     * @param strategiesNames all strategies
     * */
    private  fun updateRatingData(strategiesNames: MutableList<String>) {
        val winTrades = repository.getTradesByResult(Results.Victory.name)
        val defeatTrades = repository.getTradesByResult(Results.Defeat.name)
        val ratingObj = RatingCounter(strategiesNames,winTrades,defeatTrades,2)
        ratingObj.updateData()
        getWinRateListLong = ratingObj.longWinRateList
        getWinRateListShort.postValue(ratingObj.shortWinRateList)

        tradeNumbers = ratingObj.tradeNumbers
        tradeShortNumbers = ratingObj.tradeShortNumbers
        tradeLongNumbers = ratingObj.tradeLongNumbers
    }
}