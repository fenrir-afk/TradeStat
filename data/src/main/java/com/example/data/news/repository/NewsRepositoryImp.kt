package com.example.data.news.repository

import com.example.data.news.dataSource.NewsDataSource
import com.example.domain.contracts.NewsRepository
import com.example.domain.model.Quotes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(private val dataSource: NewsDataSource):NewsRepository {
    override fun getForexData(quotePair: String, time: String): Flow<Quotes> {
        return dataSource.getForexData(quotePair,time)
    }
}