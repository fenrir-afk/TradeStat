package com.example.presentation.ui.instrumentStatistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.instrument.usecase.GetInstrumentsRatingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstrumentViewModel @Inject constructor(
    private val getInstrumentsRatingUseCase: GetInstrumentsRatingUseCase,
) : ViewModel(){
        private var _winRateFlow = MutableStateFlow<List<Int>>(emptyList())
        val winRateFlow = _winRateFlow.asStateFlow()

        private var _winRateShortFlow = MutableStateFlow<List<Int>>(emptyList())
        val winRateShortFlow = _winRateFlow.asStateFlow()

        var getWinRateListLong: MutableList<Int> = mutableListOf()
        var instrumentsNames: MutableList<String> = mutableListOf()

        var tradeNumbers = mutableListOf<Int>()
        var tradeShortNumbers = mutableListOf<Int>()
        var tradeLongNumbers = mutableListOf<Int>()

    /**
     * In this method we update  data int the viewModel.It need for button realization
     * */
     fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            val ratingObj = getInstrumentsRatingUseCase.execute()
            instrumentsNames = ratingObj.names.toMutableList()
            getWinRateListLong = ratingObj.longWinRateList

            _winRateShortFlow.emit(ratingObj.shortWinRateList)
            _winRateFlow.emit(ratingObj.winRateList)

            tradeNumbers = ratingObj.tradeNumbers
            tradeShortNumbers = ratingObj.tradeShortNumbers
            tradeLongNumbers = ratingObj.tradeLongNumbers

        }
    }

}