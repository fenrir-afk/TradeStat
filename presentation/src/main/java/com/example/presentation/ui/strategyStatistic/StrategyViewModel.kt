package com.example.presentation.ui.strategyStatistic



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.BaseRepository
import com.example.domain.model.Results
import com.example.domain.strategy.usecase.GetAllTradesDescendingUseCase
import com.example.domain.strategy.usecase.GetDefeatTradesUseCase
import com.example.domain.strategy.usecase.GetStrategiesListUseCase
import com.example.domain.strategy.usecase.GetWinTradesUseCase
import com.example.presentation.utils.RatingCounter

import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StrategyViewModel @Inject constructor(
    private val getAllTradesDescendingUseCase: GetAllTradesDescendingUseCase,
    private val getDefeatTradesUseCase: GetDefeatTradesUseCase,
    private val getStrategiesListUseCase: GetStrategiesListUseCase,
    private val getWinTradesUseCase: GetWinTradesUseCase
) : ViewModel() {
    private var _winRateShortFlow = MutableStateFlow<MutableList<Int>>(mutableListOf())
    val winRateShortFlow = _winRateShortFlow.asStateFlow()

    private var _entriesFlow = MutableStateFlow<MutableList<List<Entry>>>(mutableListOf())
    val entriesFlow = _entriesFlow.asStateFlow()


    var getWinRateListLong: MutableList<Int> = mutableListOf()
    var tradeLongNumbers: MutableList<Int> = mutableListOf()
    var tradeShortNumbers: MutableList<Int> =  mutableListOf()
    private var tradeNumbers: MutableList<Int> =  mutableListOf()
    var strategiesNames: MutableList<String> = mutableListOf()


    /**
     * In this method we get coordinates relatively to the trade list
    * */
     fun updateData(){
        viewModelScope.launch(Dispatchers.IO) {
            strategiesNames = mutableListOf()
            val strategies = getStrategiesListUseCase.execute()
            val trades = getAllTradesDescendingUseCase.execute()
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
            _entriesFlow.emit(strategyLists)
            updateRatingData(strategiesNames)
        }

    }
    /**
     * In this method we update rating data int the viewModel
     * @param strategiesNames all strategies
     * */
    private  fun updateRatingData(strategiesNames: MutableList<String>) {
        viewModelScope.launch(Dispatchers.IO){
            val winTrades = getWinTradesUseCase.execute()
            val defeatTrades = getDefeatTradesUseCase.execute()
            val ratingObj = RatingCounter(strategiesNames,winTrades,defeatTrades,2)
            ratingObj.updateData()
            getWinRateListLong = ratingObj.longWinRateList
            _winRateShortFlow.emit(ratingObj.shortWinRateList)

            tradeNumbers = ratingObj.tradeNumbers
            tradeShortNumbers = ratingObj.tradeShortNumbers
            tradeLongNumbers = ratingObj.tradeLongNumbers
        }
    }
}