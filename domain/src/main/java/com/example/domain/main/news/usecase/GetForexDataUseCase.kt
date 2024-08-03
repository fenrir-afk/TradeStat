package com.example.domain.main.news.usecase

import com.example.domain.contracts.NewsRepository
import com.example.domain.model.Quotes
import kotlinx.coroutines.flow.Flow

class GetForexDataUseCase(val rep: NewsRepository) {
    fun execute(quotes: String, time: String): Flow<Quotes> {
        return rep.getForexData(quotes, time)
    }
}