package com.example.tradestat.ui.instrumentStatistic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import com.example.tradestat.data.model.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InstrumentViewModel(application: Application) : AndroidViewModel(application){
        val getWinRateList: MutableLiveData<List<Int> > = MutableLiveData()
        var instrumentsNames: MutableList<String> = mutableListOf()
        private val repository: TradesRepository
        init {
            val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
            val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
            val instrumentDao = TradeDatabase.getDatabase(application).getInstrumentDao()
            repository = TradesRepository(tradeDao,strategyDao,instrumentDao)
            updateData()
        }
        fun updateData(){
            viewModelScope.launch(Dispatchers.IO) {
                val instruments = repository.getInstrumentList()
                instruments.forEach{
                    instrumentsNames.add(it.instrumentName)
                }
                val winRateList = mutableListOf<Int>()
                for (instrumentsName in instrumentsNames) {
                    val wins = repository.getCountTradesByInstrumentAndResult(Results.Victory.name,instrumentsName)
                    val defeats = repository.getCountTradesByInstrumentAndResult(Results.Defeat.name,instrumentsName)
                    if (wins+defeats != 0){
                        val rating = wins * 100/(wins+defeats)
                        winRateList.add(rating)
                    }else{
                        winRateList.add(0)
                    }

                }
                getWinRateList.postValue(winRateList)
            }
        }
}