package com.example.tradestat.ui.analysis

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Trade
import com.example.tradestat.repository.BaseRepository
import com.example.tradestat.utils.RatingCounter
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnalysisViewModel(application: Application,rep: BaseRepository) : AndroidViewModel(application)  {
    val  list: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    var bestStrategy:String = ""
    var bestInstrument:String = ""
    var tradeResult:Int = 0
    private val repository: BaseRepository = rep
    fun updateData(){ // in this method we get coordinates relatively to the trade list
        viewModelScope.launch(Dispatchers.IO) {
            val allTrades  = repository.getTradesSortedByDateDescending()
            if (allTrades.isNotEmpty()){
                val winTrades  = repository.getTradesByResult(Results.Victory.name)
                val defeatTrades  = repository.getTradesByResult(Results.Defeat.name)
                tradeResult = winTrades.size - defeatTrades.size
                bestInstrument = getMaxName(winTrades,defeatTrades,1)
                bestStrategy = getMaxName(winTrades,defeatTrades,2)
                getCoordinates(allTrades,list)
            }
        }
    }
    private fun getMaxName(winTrades:List<Trade>, defeatTrades: List<Trade>, token:Int): String {
        val names = mutableListOf<String>()
        if (token == 1){
            val list =  repository.getAllInstruments()
            list.forEach{
                names.add(it.instrumentName)
            }
        }else{
            val list =  repository.getAllStrategies()
            list.forEach{
                names.add(it.strategyName)
            }
        }
        val obj = RatingCounter(names,winTrades,defeatTrades,token) //instruments
        val maxRate = obj.winRateList.max()
        var maxIndex = 0
        obj.winRateList.forEachIndexed { index, i -> //looking for index of the max value
            if (i == maxRate){
                maxIndex = index
            }
        }
        return names[maxIndex]
    }
    private fun getCoordinates(
        tradeArr: List<Trade>,
        finalList: MutableLiveData<MutableList<Entry>>
    ) {
        val list:MutableList<Entry> = mutableListOf()
        list.add(Entry(0f,0f))
        var verticalCounter = 0f
        var horizontalCounter: Float
        repeat(tradeArr.size) {
            horizontalCounter = it.toFloat() + 1f
            if (tradeArr[it].tradeResult == Results.Victory.name){
                verticalCounter++
            }else{
                verticalCounter--
            }
            list.add(Entry(horizontalCounter,verticalCounter))
        }
        finalList.postValue(list)
    }
}