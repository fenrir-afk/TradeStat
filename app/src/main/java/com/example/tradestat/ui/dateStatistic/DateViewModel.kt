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
import kotlinx.coroutines.launch

class DateViewModel(application: Application,rep: BaseRepository) : AndroidViewModel(application) {
    val  RatingList: MutableLiveData<MutableList<Int>> = MutableLiveData()
    val  mondayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  thursdayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  tuesdayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  wednesdayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  fridayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  saturdayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val  sundayList: MutableLiveData<MutableList<Entry>> = MutableLiveData()

    private val repository: BaseRepository = rep
    fun updateDay(){ // in this method we get coordinates relatively to the trade list
        viewModelScope.launch(Dispatchers.IO) {
            val mondayArr  = repository.getDayStatistic(DaysOfWeek.Monday)
            val thursdayArr  = repository.getDayStatistic(DaysOfWeek.Thursday)
            val tuesdayArr  = repository.getDayStatistic(DaysOfWeek.Tuesday)
            val wednesdayArr  = repository.getDayStatistic(DaysOfWeek.Wednesday)
            val fridayArr  = repository.getDayStatistic(DaysOfWeek.Friday)
            val saturdayArr  = repository.getDayStatistic(DaysOfWeek.Saturday)
            val sundayArr  = repository.getDayStatistic(DaysOfWeek.Sunday)
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