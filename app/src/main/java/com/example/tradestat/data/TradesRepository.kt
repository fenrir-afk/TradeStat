package com.example.tradestat.data

import androidx.lifecycle.LiveData
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade

class TradesRepository(private val tradesDao: TradesDao,private val strategiesDao: StrategiesDao) {
    val readAllData:LiveData<List<Trade>> = tradesDao.getAll()
    suspend fun addTrade(trade: Trade){
        tradesDao.insert(trade)
    }
    suspend fun deleteTrade(trade: Trade){
        tradesDao.delete(trade)
    }
    suspend fun readStrategies(): List<Strategy> {
        return strategiesDao.getAllStrategies()
    }

    suspend fun addStrategy(strategy: Strategy) {
        strategiesDao.insertStrategy(strategy)
    }

    suspend fun deleteStrategy(strategy: String) {
        strategiesDao.deleteStrategyByName(strategy)
    }
}