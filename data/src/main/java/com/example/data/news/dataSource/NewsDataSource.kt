package com.example.data.news.dataSource

import com.example.domain.model.Quotes
import kotlinx.coroutines.flow.Flow

interface NewsDataSource {
    fun getForexData(quotePair: String, time: String): Flow<Quotes>
}