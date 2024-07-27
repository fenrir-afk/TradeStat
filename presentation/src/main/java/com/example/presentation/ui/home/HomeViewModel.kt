package com.example.presentation.ui.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class HomeViewModel(rep: BaseRepository) : ViewModel() {
    private val _numberListFlow = MutableStateFlow<List<Int>>(emptyList())
    val numberListFlow = _numberListFlow.asStateFlow()

    private var shortNumber: Int = 0
    private var longNumber: Int = 0
    private var winNumber: Int = 0
    private var defeatNumber: Int = 0
    private val repository: BaseRepository = rep
    /**
     * In this method we are getting  data from bd
     * */
    fun updateNumbers(){
        viewModelScope.launch(Dispatchers.IO) {
            shortNumber = repository.getShortPos()
            longNumber = repository.getLongPos()
            winNumber = repository.getWinNumber() // Victories
            defeatNumber = repository.getDefNumber() //Defeats
            _numberListFlow.emit(listOf(shortNumber,longNumber,winNumber,defeatNumber))
        }
    }
}