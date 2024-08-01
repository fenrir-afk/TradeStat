package com.example.presentation.ui.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.trade.usecase.GetDefeatNumberUseCase
import com.example.domain.trade.usecase.GetLongNumberUseCase
import com.example.domain.trade.usecase.GetShortNumberUseCase
import com.example.domain.trade.usecase.GetWinNumberUseCase
import com.example.presentation.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getShortUseCase:GetShortNumberUseCase,
    private val getLongUseCase: GetLongNumberUseCase,
    private val getWinUseCase:GetWinNumberUseCase,
    private val getDefeatUseCase: GetDefeatNumberUseCase,
) : ViewModel() {
    private val _numberListFlow = MutableStateFlow<List<Int>>(emptyList())
    val numberListFlow = _numberListFlow.asStateFlow()

    private var shortNumber: Int = 0
    private var longNumber: Int = 0
    private var winNumber: Int = 0
    private var defeatNumber: Int = 0
    /**
     * In this method we are getting  data from bd
     * */
    fun updateNumbers(){
        viewModelScope.launch(Dispatchers.IO) {
            shortNumber = getShortUseCase.execute()
            longNumber = getLongUseCase.execute()
            winNumber = getWinUseCase.execute()// Victories
            defeatNumber = getDefeatUseCase.execute()//Defeats
            _numberListFlow.emit(listOf(shortNumber,longNumber,winNumber,defeatNumber))
        }
    }
}