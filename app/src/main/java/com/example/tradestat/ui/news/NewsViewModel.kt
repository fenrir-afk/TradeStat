package com.example.tradestat.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.StringReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import javax.xml.parsers.DocumentBuilderFactory

class NewsViewModel(rep: BaseRepository) : ViewModel() {
    private val repository: BaseRepository = rep
    var quote: MutableList<Triple<String,Boolean,String>> = mutableListOf()
    val quotesLiveData: MutableLiveData<MutableList<Triple<String,Boolean,String>>> = MutableLiveData()
    val stockMarketValues: MutableLiveData<MutableMap<String,String>> = MutableLiveData()
    init {
        updateQuotes("USD/RUB","1h")
        updateQuotes("CNY/RUB","1h")
        getMoexData()
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
    fun getMoexData() {
        viewModelScope.launch(Dispatchers.IO) {
            val url = URL("https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities.xml?iss.meta=off&iss.only=securities&securities.columns=SECID,PREVLEGALCLOSEPRICE")

            try {
                val connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"

                if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val xmlString = reader.readText()
                    // Парсинг XML и поиск нужного значения
                    val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                        InputSource(StringReader(xmlString))
                    )
                    val nodeList = document.getElementsByTagName("row")
                    val map = mutableMapOf<String,String>()
                    map["MOEX"] = getValueByName(nodeList,"MOEX")
                    map["SBER"] = getValueByName(nodeList,"SBER")
                    map["GAZP"] = getValueByName(nodeList,"GAZP")
                    stockMarketValues.postValue(map)
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
    private fun getValueByName(nodeList: NodeList, name: String): String {
        for (i in 0 until nodeList.length) {
            val node = nodeList.item(i)
            val secId = node.attributes.getNamedItem("SECID").nodeValue
            if (secId == name) {
                val prevLegalClosePrice = node.attributes.getNamedItem("PREVLEGALCLOSEPRICE").nodeValue
                return prevLegalClosePrice
            }
        }
        return ""
    }
}