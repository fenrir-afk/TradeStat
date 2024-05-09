package com.example.tradestat.ui.news

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
    /**
     * In this method we aare parsing rb.ru
     * @param url url that we parse https://rb.ru/...
     * */
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
                val deferredTasks = parsedMap.entries.map { newsLink ->
                    async {
                        val baseUrl = "https://rb.ru"
                        var article = ""
                        var imageUrl: String? = null
                        val link = "$baseUrl$newsLink"
                        try {
                            val newsDoc = Jsoup.connect(link).get()
                            val metaElement = newsDoc.select("meta[content~=https://media.rbcdn.ru/media/news/]").first()
                            imageUrl = metaElement?.attr("content")
                            val divElement = newsDoc.select("div.article__introduction")[1]
                            //delete last 2 sentences
                            val lastSentenceIndex = divElement.text().lastIndexOf('.')
                            val secondToLastSentenceIndex = divElement.text().substring(0, lastSentenceIndex).lastIndexOf('.')
                            article = divElement.text().substring(0, secondToLastSentenceIndex + 1)
                            articleMap[article] = newsLink.value
                            imageUrl?.let { imageMap[it] = newsLink.value }
                            linkMap[link] = newsLink.value
                        } catch (e: Exception) {
                            Log.e("Exception during parsing: ", "Error for $link: ${e.message}")
                        }

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
    /**
     * In this method we are getting list and sort it by Date
     * @return we return sorted by  Date list
     * @param list unsorted map
     * @param token there are 2 variants:1 - update date list too,other values mean that we dont update date list
     * */
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