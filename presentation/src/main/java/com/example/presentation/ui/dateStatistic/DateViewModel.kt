package com.example.presentation.ui.dateStatistic
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.date.usecase.GetCoordinatesByArrUseCase
import com.example.domain.date.usecase.GetDayStatisticsUseCase
import com.example.domain.date.usecase.GetWinRateByDayUseCase
import com.example.domain.model.DaysOfWeek
import com.example.domain.model.Trade
import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
    private val getWinRateByDayUseCase: GetWinRateByDayUseCase,
    private val getCoordinatesByArrUseCase: GetCoordinatesByArrUseCase,
    private val getDayStatisticsUseCase: GetDayStatisticsUseCase
) : ViewModel(){

    private val _dayEntriesFlow = MutableStateFlow(mutableListOf<MutableList<Entry>>()) // private mutable state flow
    private val daysEntriesArr = mutableListOf<MutableList<Entry>>() //list of lists each of which represent entries of single day
    val combinedDayFlow = _dayEntriesFlow.asStateFlow() // publicly exposed as read-only state flow

    private val _ratingFlow = MutableStateFlow(mutableListOf<Int>()) // this flow contains rating of each day
    val ratingFlow = _ratingFlow.asStateFlow()

    init {
        updateDay()
        getRatingList()
    }
    private fun updateDay(){ // in this method we get coordinates relatively to the trade list
        viewModelScope.launch(Dispatchers.IO) {
            getDayStatisticsUseCase.execute()
                .flowOn(Dispatchers.IO)
                .collect {
                    getCoordinates1(it)
                }
            _dayEntriesFlow.value = daysEntriesArr
        }
    }
    private fun getRatingList(){ // in this method we get coordinates relatively to the trade list
        viewModelScope.launch(Dispatchers.IO) {
            val arr = mutableListOf<Int>()
            DaysOfWeek.entries.forEach{
                arr.add(getWinRate(it))
            }
            _ratingFlow.value = arr
        }
    }
    private fun getCoordinates1(
        tradeArr: List<Trade>
    ) {
        val list = getCoordinatesByArrUseCase.execute(tradeArr).map { Entry(it.first,it.second)}.toMutableList()
        daysEntriesArr.add(list)
    }
    private fun getWinRate(daysOfWeek: DaysOfWeek): Int {
       return getWinRateByDayUseCase.execute(daysOfWeek)
    }

}