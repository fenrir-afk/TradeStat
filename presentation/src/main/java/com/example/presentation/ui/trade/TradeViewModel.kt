package com.example.presentation.ui.trade


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.main.trade.usecase.AddInstrumentUseCase
import com.example.domain.main.trade.usecase.AddStrategyUseCase
import com.example.domain.main.trade.usecase.AddTradeUseCase
import com.example.domain.main.trade.usecase.DeleteInstrumentUseCase
import com.example.domain.main.trade.usecase.DeleteStrategyUseCase
import com.example.domain.main.trade.usecase.DeleteTradeUseCase
import com.example.domain.main.trade.usecase.GetAllInstrumentsUseCase
import com.example.domain.main.trade.usecase.GetAllStrategiesUseCase
import com.example.domain.main.trade.usecase.GetAscendingTradesUseCase
import com.example.domain.main.trade.usecase.GetDescendingTradesUseCase
import com.example.domain.main.trade.usecase.SortByInstrumentUseCase
import com.example.domain.main.trade.usecase.SortByStrategyUseCase
import com.example.domain.model.Instrument
import com.example.domain.model.Strategy
import com.example.domain.model.Trade
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeViewModel @Inject constructor(
    private val addTradeUseCase: AddTradeUseCase,
    private val deleteTradeUseCase: DeleteTradeUseCase,
    private val getDescendingTradesUseCase: GetDescendingTradesUseCase,
    private val getAscendingTradesUseCase: GetAscendingTradesUseCase,
    private val sortByStrategyUseCase: SortByStrategyUseCase,
    private val sortByInstrumentUseCase: SortByInstrumentUseCase,
    private val getAllStrategiesUseCase: GetAllStrategiesUseCase,
    private val getAllInstrumentsUseCase: GetAllInstrumentsUseCase,
    private val deleteStrategiesUseCase: DeleteStrategyUseCase,
    private val deleteInstrumentUseCase: DeleteInstrumentUseCase,
    private val addStrategyUseCase: AddStrategyUseCase,
    private val addInstrumentUseCase: AddInstrumentUseCase
) : ViewModel() {

    private var _sortedTradesListFlow = MutableStateFlow<List<Trade>>(emptyList())
    val sortedTradesListFlow = _sortedTradesListFlow.asStateFlow()

    var getStrategyList:List<Strategy> = arrayListOf()
    var getInstrumentList:List<Instrument> = arrayListOf()



    //trade section
    fun addTrade(trade: Trade){
        viewModelScope.launch(Dispatchers.IO) {
            addTradeUseCase.execute(trade)
            _sortedTradesListFlow.emit(getAscendingTradesUseCase.execute())
        }
    }
    fun deleteTrade(trade: Trade){
        viewModelScope.launch(Dispatchers.IO) {
            deleteTradeUseCase.execute(trade)
            var instrumentCounter =0
            var strategyCounter =0
            getDescendingTradesUseCase.execute()
            getDescendingTradesUseCase.execute().forEach{
                if (it.strategy == trade.strategy){
                    strategyCounter++
                }
                if (it.instrument == trade.instrument){
                    instrumentCounter++
                }
            }
            if (strategyCounter == 0){
                deleteStrategiesUseCase.execute(trade.strategy)
            }
            if (instrumentCounter == 0){
                deleteInstrumentUseCase.execute(trade.instrument)
            }
            _sortedTradesListFlow.emit(getAscendingTradesUseCase.execute())//update list
        }
    }
    /**
     * In this method we get actual trade list sorted by date
     * */
    fun updateListByDateDescending(){
        viewModelScope.launch(Dispatchers.IO) {
            _sortedTradesListFlow.emit(getDescendingTradesUseCase.execute())
        }
    }
    /**
     * In this method we get actual trade list sorted by date
     * */
    fun updateListByDateAscending(){
        viewModelScope.launch(Dispatchers.IO) {
            _sortedTradesListFlow.emit(getAscendingTradesUseCase.execute())
        }
    }
    /**
     * In this method we get actual trade list sorted by strategy
     * */
    fun updateListByStrategy(strategy: String){
      viewModelScope.launch(Dispatchers.IO) {
          _sortedTradesListFlow.emit(sortByStrategyUseCase.execute(strategy))
        }
    }
    /**
     * In this method we get actual trade list sorted by instrument
     * */
    fun updateListByInstrument(instrument: String){
        viewModelScope.launch(Dispatchers.IO) {
            _sortedTradesListFlow.emit(sortByInstrumentUseCase.execute(instrument))
        }
    }
    //strategies section

    fun redStrategiesFromRepository(){
        viewModelScope.launch(Dispatchers.IO) {
            getStrategyList = getAllStrategiesUseCase.execute()
        }
    }
    fun addStrategy(strategy: Strategy){
        viewModelScope.launch(Dispatchers.IO) {
            addStrategyUseCase.execute(strategy)
        }
    }

    //instrument section

    fun readInstrumentsFromRepository(){
        viewModelScope.launch(Dispatchers.IO) {
            getInstrumentList = getAllInstrumentsUseCase.execute()
        }
    }
    fun addInstrument(instrument: Instrument){
        viewModelScope.launch(Dispatchers.IO) {
            addInstrumentUseCase.execute(instrument)
        }
    }

}