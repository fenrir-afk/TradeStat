package com.example.presentation.ui.analysis


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.analysis.usecase.GetAnalysisChartUseCase
import com.example.domain.analysis.usecase.GetMaxNameUseCase
import com.example.domain.main.home.usecase.GetDefeatNumberUseCase
import com.example.domain.main.home.usecase.GetWinNumberUseCase
import com.example.domain.main.trade.usecase.GetDescendingTradesUseCase
import com.example.domain.model.Trade
import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val getMaxNameUseCase: GetMaxNameUseCase,
    private val getAnalysisChartUseCase: GetAnalysisChartUseCase,
    private val descendingTradesUseCase: GetDescendingTradesUseCase,
    private val getWinNumberUseCase: GetWinNumberUseCase,
    private val getDefeatNumberUseCase: GetDefeatNumberUseCase
) : ViewModel()  {
    private val _entryListFlow = MutableStateFlow<List<Entry>>(listOf())
    val entryListFlow = _entryListFlow.asStateFlow()
    var bestStrategy:String = ""
    var bestInstrument:String = ""
    var tradeResult:Int = 0
    fun updateData(){ // in this method we get coordinates relatively to the trade list
        viewModelScope.launch(Dispatchers.IO) {
            val allTrades  = descendingTradesUseCase.execute()
            if (allTrades.isNotEmpty()){
                tradeResult = getWinNumberUseCase.execute() - getDefeatNumberUseCase.execute()
                bestInstrument = getMaxNameUseCase.execute(1)
                bestStrategy = getMaxNameUseCase.execute(2)
                getCoordinates(allTrades)
            }
        }
    }
    private fun getCoordinates(
        tradeArr: List<Trade>,
    ) {
        viewModelScope.launch(Dispatchers.Default){
             val entryArr = getAnalysisChartUseCase.execute(tradeArr).map { Entry(it.first,it.second) }
            _entryListFlow.emit(entryArr)
        }
    }
}