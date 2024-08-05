package com.example.domain.contracts


import com.example.domain.model.Quotes
import kotlinx.coroutines.flow.Flow


interface NewsRepository {
    fun getForexData(quotePair: String, time: String): Flow<Quotes>
}