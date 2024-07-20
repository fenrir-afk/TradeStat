package com.example.tradestat.ui.results


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Trade
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ResultsViewModel(rep: BaseRepository): ViewModel() {
    private val repository: BaseRepository = rep
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
                currentRating.add(getRating(currentMonthTrades,it,2))
                previousRating.add(getRating(previousMonthTrades,it,2))
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
            currentRating.add(getRating(currentMonthTrades,it,1))
            previousRating.add(getRating(previousMonthTrades,it,1))
        }
        previousMonthInstrumentsRating = previousRating
        currentMonthInstrumentsRating = currentRating

    }
    /**
     * In this method we are getting rating for the single instrument of strategy
     * @param list list of trades(current month or previous month)
     * @param name name of the strategy or instrument
     * @toke 1 is a token for instruments other numbers for strategies
     * @return returns rating of name param
     * */
    private fun getRating(list: MutableList<Trade>, name: String,token:Int): Int {
        var wins = 0
        var defeats = 0
        list.forEach {
            if (token == 1){
                if (it.instrument == name){
                    if (it.tradeResult == Results.Victory.name){
                        wins++
                    }else{
                        defeats++
                    }
                }
            }else{
                if (it.strategy == name){
                    if (it.tradeResult == Results.Victory.name){
                        wins++
                    }else{
                        defeats++
                    }
                }
            }

        }
        return if (wins + defeats == 0){
            0
        }else{
            (wins * 100)/(wins + defeats)
        }
    }
    /**
     * In this method we are getting array of previous month or current month based on token
     * @param token 1 mean current month other numbers mean previous month
     * @return return all trades added in the month
     * @param trades all trades of app
     * */
    private fun updateTradesLists(trades: List<Trade>,token: Int): MutableList<Trade> {
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val lastMonth = if (currentMonth == 0) 11 else currentMonth - 1
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val dealsThisMonth = trades.filter { trade ->
            val tradeDate = dateFormat.parse(trade.ADDate)
            val dealMonth = tradeDate?.month ?: -1
            val dealYear = tradeDate?.year?.plus(1900)
            if (token == 1){
                dealMonth == currentMonth && dealYear == currentYear
            }else{
                dealMonth == lastMonth && dealYear == currentYear
            }
        }
        return dealsThisMonth as MutableList<Trade>
    }
    /**
     * In this method we are getting arrays of current and previous months and update strategies and instruments list
     * */
     fun updateLists() {
        viewModelScope.launch(Dispatchers.IO) {
            val trades = repository.getTradesSortedByDateAscending()
            currentMonthTrades =  updateTradesLists(trades,1) //1 is a token for current month
            previousMonthTrades = updateTradesLists(trades,2) //2 is a token for previous month
            updateInstrumentsLists()
            updateStrategiesLists()
        }
    }

}