package com.example.domain.news

import com.example.domain.news.entity.Quotes
import kotlinx.coroutines.flow.Flow


interface NewsRepository {
    fun getForexData(quotePair: String, time: String): Flow<Quotes>
}