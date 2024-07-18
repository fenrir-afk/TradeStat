package com.example.tradestat.ui.dateStatistic
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.DaysOfWeek
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Trade
import com.example.tradestat.repository.BaseRepository
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DateViewModel(application: Application,rep: BaseRepository) : AndroidViewModel(application) {
    val  RatingList: MutableLiveData<MutableList<Int>> = MutableLiveData()

    private val _combinedDayFlow = MutableStateFlow(mutableListOf<MutableList<Entry>>()) // private mutable state flow
    private val arr = mutableListOf<MutableList<Entry>>()
    val combinedDayFlow = _combinedDayFlow.asStateFlow() // publicly exposed as read-only state flow

    private val repository: BaseRepository = rep
    fun updateDay(){ // in this method we get coordinates relatively to the trade list
        viewModelScope.launch(Dispatchers.Main) {
            repository.getDayStatistic()
                .flowOn(Dispatchers.IO)
                .collect {
                    getCoordinates1(it)
                }
            _combinedDayFlow.value = arr
        }
    }
    private fun getCoordinates1(
        tradeArr: List<Trade>
    ) {
        val list:MutableList<Entry> = mutableListOf()
        list.add(Entry(0f,0f))
        var verticalCounter = 0f
        var horizontalCounter: Float
        repeat(tradeArr.size) {
            horizontalCounter = it.toFloat() + 1f
            if (tradeArr[it].tradeResult == Results.Victory.name){
                verticalCounter++
            }else{
                verticalCounter--
            }
            list.add(Entry(horizontalCounter,verticalCounter))
        }
        arr.add(list)
    }
    private fun getCoordinates(
        tradeArr: List<Trade>,
        finalList: MutableLiveData<MutableList<Entry>>
    ) {
        val list:MutableList<Entry> = mutableListOf()
        list.add(Entry(0f,0f))
        var verticalCounter = 0f
        var horizontalCounter: Float
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
    fun getRatingList(){ // in this method we get coordinates relatively to the trade list
        viewModelScope.launch(Dispatchers.IO) {
            val arr = mutableListOf<Int>()
            arr.add(getWinRate(DaysOfWeek.Monday))
            arr.add(getWinRate(DaysOfWeek.Tuesday))
            arr.add(getWinRate(DaysOfWeek.Wednesday))
            arr.add(getWinRate(DaysOfWeek.Thursday))
            arr.add(getWinRate(DaysOfWeek.Friday))
            arr.add(getWinRate(DaysOfWeek.Saturday))
            arr.add(getWinRate(DaysOfWeek.Sunday))
            RatingList.postValue(arr)
        }
    }
    private fun getWinRate(daysOfWeek: DaysOfWeek): Int {
        val victories  = repository.countTradesByResultAndDate(Results.Victory,daysOfWeek)
        val defeats  = repository.countTradesByResultAndDate(Results.Defeat,daysOfWeek)
        if (defeats != 0){
            return victories*100/(defeats + victories)
        }
        if (victories == 0){
            return 0
        }
        return 100

    }

}