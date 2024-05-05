package com.example.tradestat.ui.results

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Trade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ResultsViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TradesRepository
    var namesList = mutableSetOf<String>()

    private var currentMonthTrades: MutableList<Trade> = mutableListOf()
    var currentMonthStrategiesRating: MutableLiveData<MutableList<Int>> = MutableLiveData()

    private var previousMonthTrades: MutableList<Trade> = mutableListOf()
    var previousMonthStrategiesRating: MutableList<Int> = mutableListOf()
    init {
        val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
        val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
        val instrumentDao = TradeDatabase.getDatabase(application).getInstrumentDao()
        repository = TradesRepository(tradeDao,strategyDao,instrumentDao)
        updateLists()
    }

    private fun updateStrategiesLists() {
        namesList = mutableSetOf()
        val currentRating = mutableListOf<Int>()
        val previousRating = mutableListOf<Int>()
        currentMonthTrades.forEach {
            namesList.add(it.strategy)
        }
        previousMonthTrades.forEach {
            namesList.add(it.strategy)
        }
        namesList.forEach{
            currentRating.add(getRating(currentMonthTrades,it,2))
            previousRating.add(getRating(previousMonthTrades,it,2))
        }
        previousMonthStrategiesRating = previousRating
        currentMonthStrategiesRating.postValue(currentRating)

    }
    private fun getRating(list: MutableList<Trade>, name: String,token:Int): Int {
        var wins = 0
        var defests = 0
        list.forEach {
            if (token == 1){
                if (it.instrument == name){
                    if (it.tradeResult == Results.Victory.name){
                        wins++
                    }else{
                        defests++
                    }
                }
            }else{
                if (it.strategy == name){
                    if (it.tradeResult == Results.Victory.name){
                        wins++
                    }else{
                        defests++
                    }
                }
            }

        }
        if (wins + defests == 0){
            return 0
        }else{
            return (wins * 100)/(wins + defests)
        }
    }
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

    private fun updateLists() {
        viewModelScope.launch(Dispatchers.IO) {
            val trades = repository.getTradesSortedByDateAscending()
            currentMonthTrades =  updateTradesLists(trades,1) //1 is a token for current month
            previousMonthTrades = updateTradesLists(trades,2) //2 is a token for previous month
            updateStrategiesLists()
        }
    }

}