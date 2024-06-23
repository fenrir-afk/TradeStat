package com.example.tradestat.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class NewsViewModel(rep: BaseRepository) : ViewModel() {
    private val repository: BaseRepository = rep
    var quote: MutableList<Triple<String,Boolean,String>> = mutableListOf()
    val quotesLiveData: MutableLiveData<MutableList<Triple<String,Boolean,String>>> = MutableLiveData()
    init {
        updateQuotes("USD/RUB","1h")
        updateQuotes("CNY/RUB","1h")
    }
    private fun updateQuotes(quotePair:String, time:String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getForexData(quotePair,time){
                val directionNumber = it.response[0].cp
                val formattedQuote = "%.1f".format(it.response[0].c.toDouble())
                if (directionNumber.first() == ('-')){
                    quote.add(Triple(formattedQuote,false,quotePair))
                }else{
                    quote.add(Triple(formattedQuote,true,quotePair))
                }
                if (quote.size == 2){
                    quotesLiveData.postValue(quote)
                }
            }
        }
    }
}