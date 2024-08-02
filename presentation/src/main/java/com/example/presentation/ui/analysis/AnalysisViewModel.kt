package com.example.presentation.ui.analysis


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.BaseRepository
import com.example.domain.model.Results
import com.example.domain.model.Trade
import com.example.presentation.utils.RatingCounter
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnalysisViewModel(rep: BaseRepository) : ViewModel()  {
    private val _entryListFlow = MutableStateFlow<MutableList<Entry>>(mutableListOf())
    val entryListFlow = _entryListFlow.asStateFlow()
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
                getCoordinates(allTrades)
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
    ) {
        viewModelScope.launch(Dispatchers.Default){
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
            _entryListFlow.emit(list)
        }
    }
}