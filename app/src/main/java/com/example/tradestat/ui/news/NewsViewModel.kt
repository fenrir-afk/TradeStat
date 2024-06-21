package com.example.tradestat.ui.news

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class NewsViewModel(rep: BaseRepository) : ViewModel() {
    private val repository: BaseRepository = rep
    var quote: String = ""
    val direction: MutableLiveData<Boolean> = MutableLiveData()
    fun updateQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getForexData {
                quote = it.response[0].c
                var directionNumber = it.response[0].cp
                if (directionNumber.first() == ('-')){
                    direction.postValue(false)
                }else{
                    direction.postValue(true)
                }
            }
        }
    }
}