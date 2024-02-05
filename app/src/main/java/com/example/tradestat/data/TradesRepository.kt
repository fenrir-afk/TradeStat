package com.example.tradestat.data

import androidx.lifecycle.LiveData
import com.example.tradestat.data.model.Trade

class TradesRepository(private val tradesDao: TradesDao) {
    val readAllData:LiveData<List<Trade>> = tradesDao.getAll()
    suspend fun addTrade(trade: Trade){
        tradesDao.insert(trade)
    }
    suspend fun deleteTrade(trade: Trade){
        tradesDao.delete(trade)
    }
}