package com.example.tradestat.ui.News

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewsViewModel : ViewModel() {
    val articlesLiveData: MutableLiveData<List<String> > = MutableLiveData()
    val dateArr: MutableList<String> = mutableListOf()
    var imgUrls: MutableList<String> = mutableListOf()
    var linkArr: MutableList<String> = mutableListOf()
    private var articleMap: MutableMap<String,LocalDate> = mutableMapOf()
    private var imageMap: MutableMap<String,LocalDate> = mutableMapOf()
    private var linkMap: MutableMap<String,LocalDate> = mutableMapOf()

    fun getNews(url: String) {
        articleMap.clear()
        imageMap.clear()
        linkMap.clear()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                 val userAgents = listOf(
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:98.0) Gecko/20100101 Firefox/98.0",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Safari/605.1.15"
                )
                val userAgent = userAgents.random()
                // Send a GET request to the URL with the User-Agent header
                val doc = Jsoup.connect(url).userAgent(userAgent).get()

                var parsedMap: MutableMap<String,LocalDate> = mutableMapOf()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val newsItems = doc.select("div.news-item")
                for (newsItem in newsItems) {
                    val newsLink = newsItem.select("div.news-item__image > a[href]").attr("href")
                    val date = newsItem.select("time.news-item__date").attr("datetime")
                    parsedMap[newsLink] = LocalDate.parse(date, formatter)

                }
                val deferredTasks = parsedMap.map { newsLink ->
                    async {
                        val newsDoc = Jsoup.connect("https://rb.ru$newsLink").get()

                        val metaElement = newsDoc.select("meta[content~=https://media.rbcdn.ru/media/news/]").first()
                        val imageUrl = metaElement?.attr("content")
                        val divElement = newsDoc.select("div.article__introduction")[1]
                        //delete last 2 sentences
                        val lastSentenceIndex = divElement.text().lastIndexOf('.')
                        val secondToLastSentenceIndex = divElement.text().substring(0, lastSentenceIndex).lastIndexOf('.')
                        val article = divElement.text().substring(0, secondToLastSentenceIndex + 1)
                        articleMap[article] = newsLink.value
                        imageMap[imageUrl.toString()] = newsLink.value
                        linkMap["https://rb.ru$newsLink"] = newsLink.value
                        article
                    }
                }
                deferredTasks.awaitAll()
                val articleArr =  updateDate(articleMap.toList(),1) //1 mean that we also update date chart
                imgUrls = updateDate(imageMap.toList(),0)
                linkArr = updateDate(linkMap.toList(),0)
                articlesLiveData.postValue(articleArr)

            } catch (e: Exception) {
                Log.e("Exception during parsing: ", "Error: ${e.message}")
            }
        }
    }

    private fun updateDate(list: List<Pair<String, LocalDate>>,token:Int): MutableList<String> {
        val sortedList = list.sortedBy { it.second }
        val articlesArr: MutableList<String> = mutableListOf()
        sortedList.reversed().toMap().toMutableMap().forEach { (t, u) ->
            articlesArr.add(t)
            if (token == 1){
                dateArr.add(u.toString())
            }
        }
        return articlesArr
    }
}