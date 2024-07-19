package com.example.tradestat.ui.home
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class HomeViewModel(application: Application,rep: BaseRepository) : AndroidViewModel(application) {
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
            winNumber = repository.getWinNumber() // победу
            defeatNumber = repository.getDefNumber() //поражение
            _numberListFlow.emit(listOf(shortNumber,longNumber,winNumber,defeatNumber))
        }
    }
}