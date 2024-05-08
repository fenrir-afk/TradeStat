package com.example.tradestat.ui.home
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val getNumberList:MutableLiveData<List<Int>> = MutableLiveData()
    private var shortNumber: Int = 0
    private var longNumber: Int = 0
    private var winNumber: Int = 0
    private var defeatNumber: Int = 0
    private val repository:TradesRepository
    init {
        val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
        val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
        val instrumentDao = TradeDatabase.getDatabase(application).getInstrumentDao()
        val noteDao = TradeDatabase.getDatabase(application).getNoteDao()
        repository = TradesRepository(tradeDao,strategyDao,instrumentDao,noteDao)
    }
    /**
     * In this method we are getting  data from bd
     * */
   fun updateNumbers(){
        viewModelScope.launch(Dispatchers.IO) {
            shortNumber = repository.getShortPos()
            longNumber = repository.getLongPos()
            winNumber = repository.getWinNumber() // победу
            defeatNumber = repository.getDefNumber() //поражение
            getNumberList.postValue(listOf(shortNumber,longNumber,winNumber,defeatNumber))
        }
    }

}