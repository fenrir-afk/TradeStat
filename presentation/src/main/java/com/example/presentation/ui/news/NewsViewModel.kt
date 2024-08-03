package com.example.presentation.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.main.news.usecase.GetForexDataUseCase
import com.example.presentation.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection
import javax.xml.parsers.DocumentBuilderFactory

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getForexDataUseCase: GetForexDataUseCase
) : ViewModel() {


    private var quote: MutableList<Triple<String,Boolean,String>> = mutableListOf()//Intermediate value for _quitesFlow
    private var _quitesFlow = MutableStateFlow<MutableList<Triple<String,Boolean,String>>>(mutableListOf())
    val quitesFlow = _quitesFlow.asStateFlow()

    private var _stockMarketValuesFlow = MutableStateFlow<MutableMap<String,String>>(mutableMapOf())
    val stockMarketValuesFlow = _stockMarketValuesFlow.asStateFlow()
    init {
        updateQuotes("USD/RUB","1h")
        updateQuotes("CNY/RUB","1h")
        getMoexData()
    }
    private fun updateQuotes(quotePair:String, time:String) {
        viewModelScope.launch(Dispatchers.IO) {
            getForexDataUseCase.execute(quotePair,time).flowOn(Dispatchers.IO).retry(3).collect{
                val directionNumber = it.response[0].cp
                val formattedQuote = "%.1f".format(it.response[0].c.toDouble())
                if (directionNumber.first() == ('-')){
                    quote.add(Triple(formattedQuote,false,quotePair))
                }else{
                    quote.add(Triple(formattedQuote,true,quotePair))
                }
                if (quote.size == 2){// ONLY when this method finish twice
                    _quitesFlow.emit(quote)
                }
            }
        }
    }
    private fun getMoexData() {
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
                    _stockMarketValuesFlow.emit(map)
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