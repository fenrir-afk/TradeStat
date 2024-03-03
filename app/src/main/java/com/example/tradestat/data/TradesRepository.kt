package com.example.tradestat.data

import androidx.lifecycle.LiveData
import com.example.tradestat.data.model.DaysOfWeek
import com.example.tradestat.data.model.Directions
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.Results
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
    suspend fun getSortedByDateList():List<Trade>{
        return tradesDao.sortByDate()
    }
    suspend fun getSortedByStrategList(strategy: String):List<Trade>{
        return tradesDao.sortByStrategy(strategy)
    }
    suspend fun getSortedByInstrumenList(instrument: String):List<Trade>{
        return tradesDao.sortByInstrument(instrument)
    }
    suspend fun getShortPos(): Int {
        return tradesDao.countTradesByDirection(Directions.Short.name)
    }

    suspend fun getLongPos(): Int {
        return  tradesDao.countTradesByDirection(Directions.Long.name)
    }

    suspend fun getWinNumber(): Int {
        return tradesDao.countTradesByResult(Results.Victory.name)
    }

    suspend fun getDefNumber(): Int {
        return  tradesDao.countTradesByResult(Results.Defeat.name)
    }

    suspend fun getDayStatistic(results: Results,day:DaysOfWeek): Int {
        return  tradesDao.countTradesByDayAndResult(results.name,day.name)
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