package com.example.presentation.ui.strategyStatistic



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.strategy.usecase.GetStrategiesChartUseCase
import com.example.domain.strategy.usecase.GetStrategiesRatingUseCase
import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StrategyViewModel @Inject constructor(
    private val getStrategiesChartUseCase: GetStrategiesChartUseCase,
    private val getStrategiesRatingUseCase: GetStrategiesRatingUseCase
) : ViewModel() {
    private var _winRateShortFlow = MutableStateFlow<MutableList<Int>>(mutableListOf())
    val winRateShortFlow = _winRateShortFlow.asStateFlow()

    private var _entriesFlow = MutableStateFlow<List<List<Entry>>>(mutableListOf())
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
            val chartEntries = getStrategiesChartUseCase.execute().map {list-> list.map { Entry(it.first,it.second) } }
            updateRatingData()
            _entriesFlow.emit(chartEntries)
        }

    }
    /**
     * In this method we update rating data int the viewModel
     * */
    private suspend fun updateRatingData() {
        viewModelScope.launch(Dispatchers.IO){
            val ratingObj = getStrategiesRatingUseCase.execute()
            ratingObj.updateData()
            strategiesNames = ratingObj.names.toMutableList()
            getWinRateListLong = ratingObj.longWinRateList
            _winRateShortFlow.emit(ratingObj.shortWinRateList)

            tradeNumbers = ratingObj.tradeNumbers
            tradeShortNumbers = ratingObj.tradeShortNumbers
            tradeLongNumbers = ratingObj.tradeLongNumbers
        }.join()
    }
}