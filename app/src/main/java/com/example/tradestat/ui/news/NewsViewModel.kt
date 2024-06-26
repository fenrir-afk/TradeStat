package com.example.tradestat.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.system.exitProcess

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
    fun getMoexData(filesDir: File) {
        viewModelScope.launch(Dispatchers.IO) {
            val url = URL("https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities.xml?iss.meta=off&iss.only=securities&securities.columns=SECID,PREVLEGALCLOSEPRICE")

            try {
                val connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"

                if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val xmlString = reader.readText()
                    val file = File(filesDir, "data.xml")
                    file.writeText(xmlString)

                } else {
                    println("Ошибка: код ответа ${connection.responseCode}")
                }

                connection.disconnect()
            } catch (e: Exception) {
                println("Ошибка: ${e.message}")
                // Обработка ошибок SSL: проверка сертификатов, обновление стека SSL/TLS
            }
        }
    }
}