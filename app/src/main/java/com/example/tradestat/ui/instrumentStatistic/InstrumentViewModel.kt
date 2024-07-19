package com.example.tradestat.ui.instrumentStatistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.Results
import com.example.tradestat.repository.BaseRepository
import com.example.tradestat.utils.RatingCounter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InstrumentViewModel(rep: BaseRepository) : ViewModel(){
        private var _winRateFlow = MutableStateFlow<List<Int>>(emptyList())
        val winRateFlow = _winRateFlow.asStateFlow()

        private var _winRateShortFlow = MutableStateFlow<List<Int>>(emptyList())
        val winRateShortFlow = _winRateFlow.asStateFlow()

        var getWinRateListLong: MutableList<Int> = mutableListOf()
        var instrumentsNames: MutableList<String> = mutableListOf()

        var tradeNumbers = mutableListOf<Int>()
        var tradeShortNumbers = mutableListOf<Int>()
        var tradeLongNumbers = mutableListOf<Int>()
        private val repository: BaseRepository = rep

    /**
     * In this method we update  data int the viewModel.It need for button realization
     * */
     fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            val instruments = repository.getAllInstruments()
            instruments.forEach{
                instrumentsNames.add(it.instrumentName)
            }
            val winTrades = repository.getTradesByResult(Results.Victory.name)
            val defeatTrades = repository.getTradesByResult(Results.Defeat.name)
            val ratingObj = RatingCounter(instrumentsNames,winTrades,defeatTrades,1)
            ratingObj.updateData()
            getWinRateListLong = ratingObj.longWinRateList

            _winRateShortFlow.emit(ratingObj.shortWinRateList)
            _winRateFlow.emit(ratingObj.winRateList)

            tradeNumbers = ratingObj.tradeNumbers
            tradeShortNumbers = ratingObj.tradeShortNumbers
            tradeLongNumbers = ratingObj.tradeLongNumbers

        }
    }

}