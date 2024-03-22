package com.example.tradestat.ui.dateStatistic
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.TradesRepository
import com.example.tradestat.data.model.DaysOfWeek
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Trade
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DateViewModel(application: Application) : AndroidViewModel(application) {
    val  mondayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  thursdayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  tuesdayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  wednesdayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  fridayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  saturdayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  sundayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()

    private val repository: TradesRepository
    init {
        val tradeDao = TradeDatabase.getDatabase(application).getTradeDao()
        val strategyDao = TradeDatabase.getDatabase(application).getStrategyDao()
        val instrumentDao = TradeDatabase.getDatabase(application).getInstrumentDao()
        repository = TradesRepository(tradeDao,strategyDao,instrumentDao)
    }
    fun updateDay(){
        viewModelScope.launch(Dispatchers.IO) {
            var mondayArr  = repository.getDayStatistic(DaysOfWeek.Monday)
            var thursdayArr  = repository.getDayStatistic(DaysOfWeek.Thursday)
            var tuesdayArr  = repository.getDayStatistic(DaysOfWeek.Tuesday)
            var wednesdayArr  = repository.getDayStatistic(DaysOfWeek.Wednesday)
            var fridayArr  = repository.getDayStatistic(DaysOfWeek.Friday)
            var saturdayArr  = repository.getDayStatistic(DaysOfWeek.Saturday)
            var sundayArr  = repository.getDayStatistic(DaysOfWeek.Sunday)
            getCoordinates(mondayArr,mondayList)
            getCoordinates(thursdayArr, thursdayList)
            getCoordinates(tuesdayArr, tuesdayList)
            getCoordinates(wednesdayArr, wednesdayList)
            getCoordinates(fridayArr, fridayList)
            getCoordinates(saturdayArr, saturdayList)
            getCoordinates(sundayArr, sundayList)
        }
    }
    private fun getCoordinates(
        tradeArr: List<Trade>,
        finalList: MutableLiveData<MutableList<Entry>>
    ) {
        var list:MutableList<Entry> = mutableListOf()
        list.add(Entry(0f,0f))
        var verticalCounter = 0f
        var horizontalCounter = 1f
        repeat(tradeArr.size) {
            horizontalCounter = it.toFloat() + 1f
            if (tradeArr[it].tradeResult == Results.Victory.name){
                verticalCounter++
            }else{
                verticalCounter--
            }
            list.add(Entry(horizontalCounter,verticalCounter))
        }
        finalList.postValue(list)
    }

}