package com.example.tradestat.data

import androidx.lifecycle.LiveData
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade

class TradesRepository(private val tradesDao: TradesDao,private val strategiesDao: StrategiesDao,private val instrumentsDao: InstrumentDao) {
    val readAllData:LiveData<List<Trade>> = tradesDao.getAll()

    //Trade part

    suspend fun addTrade(trade: Trade){
        tradesDao.insert(trade)
    }
    suspend fun deleteTrade(trade: Trade){
        tradesDao.delete(trade)
    }

    //Strategy part

    suspend fun readStrategies(): List<Strategy> {
        return strategiesDao.getAllStrategies()
    }

    suspend fun addStrategy(strategy: Strategy) {
        strategiesDao.insertStrategy(strategy)
    }

    suspend fun deleteStrategy(strategy: String) {
        strategiesDao.deleteStrategyByName(strategy)
    }

    //instrument part

    suspend fun readInstruments(): List<Instrument> {
        return instrumentsDao.getAllInstruments()
    }

    suspend fun addInstrument(instrument: Instrument) {
        instrumentsDao.insertInstrument(instrument)
    }

    suspend fun deleteInstrument(instrument: String) {
        instrumentsDao.deleteInstrumentByName(instrument)
    }
}