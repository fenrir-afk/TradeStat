package com.example.presentation.ui.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.main.trade.usecase.GetAscendingTradesUseCase
import com.example.domain.model.Trade
import com.example.domain.results.usecase.GetMonthTradesUseCase
import com.example.domain.results.usecase.GetRatingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val getAscendingTradesUseCase: GetAscendingTradesUseCase,
    private val getMonthTradesUseCase: GetMonthTradesUseCase,
    private val getRatingUseCase: GetRatingUseCase,
): ViewModel() {

    var strategiesNames = mutableSetOf<String>()
    var instrumentNames = mutableSetOf<String>()

    private var _currentMonthStrategiesRatingFlow = MutableStateFlow<MutableList<Int>>(mutableListOf())
    var currentMonthStrategiesRatingFlow = _currentMonthStrategiesRatingFlow.asStateFlow()
    private var currentMonthTrades: MutableList<Trade> = mutableListOf()
    var currentMonthInstrumentsRating: MutableList<Int> = mutableListOf()

    private var previousMonthTrades: MutableList<Trade> = mutableListOf()
    var previousMonthStrategiesRating: MutableList<Int> = mutableListOf()
    var previousMonthInstrumentsRating: MutableList<Int> = mutableListOf()

    /**
     * In this method we are getting all unique strategies and update instruments lists
     * */
    private fun updateStrategiesLists() {
        viewModelScope.launch(Dispatchers.Default){
            strategiesNames = mutableSetOf()
            val currentRating = mutableListOf<Int>()
            val previousRating = mutableListOf<Int>()
            currentMonthTrades.forEach {
                strategiesNames.add(it.strategy)
            }
            previousMonthTrades.forEach {
                strategiesNames.add(it.strategy)
            }
            strategiesNames.forEach{
                currentRating.add(getRatingUseCase.execute(currentMonthTrades,it,2))
                previousRating.add(getRatingUseCase.execute(previousMonthTrades,it,2))
            }
            previousMonthStrategiesRating = previousRating
            _currentMonthStrategiesRatingFlow.emit(currentRating)
        }

    }
    /**
     * In this method we are getting all unique instruments and update instruments lists
     * */
    private fun updateInstrumentsLists() {
        instrumentNames = mutableSetOf()
        val currentRating = mutableListOf<Int>()
        val previousRating = mutableListOf<Int>()
        currentMonthTrades.forEach {
            instrumentNames.add(it.instrument)
        }
        previousMonthTrades.forEach {
            instrumentNames.add(it.instrument)
        }
        instrumentNames.forEach{
            currentRating.add(getRatingUseCase.execute(currentMonthTrades,it,1))
            previousRating.add(getRatingUseCase.execute(previousMonthTrades,it,1))
        }
        previousMonthInstrumentsRating = previousRating
        currentMonthInstrumentsRating = currentRating

    }
    /**
     * In this method we are getting arrays of current and previous months and update strategies and instruments list
     * */
     fun updateLists() {
        viewModelScope.launch(Dispatchers.IO) {
            val trades = getAscendingTradesUseCase.execute()
            currentMonthTrades =  getMonthTradesUseCase.execute(trades,1) //1 is a token for current month
            previousMonthTrades = getMonthTradesUseCase.execute(trades,2) //2 is a token for previous month
            updateInstrumentsLists()
            updateStrategiesLists()
        }
    }

}