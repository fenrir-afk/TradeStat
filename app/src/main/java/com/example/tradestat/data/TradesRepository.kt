package com.example.tradestat.data

import com.example.tradestat.data.model.DaysOfWeek
import com.example.tradestat.data.model.Directions
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade

class TradesRepository(private val tradesDao: TradesDao,private val strategiesDao: StrategiesDao,private val instrumentsDao: InstrumentDao) {
    //Trade part
    fun addTrade(trade: Trade){
        tradesDao.insert(trade)
    }
    fun deleteTrade(trade: Trade){
        tradesDao.delete(trade)
    }
    fun getSortedByDateDescending():List<Trade>{
        return tradesDao.sortByDate()
    }
    fun getSortedByDateAscending():List<Trade>{
        return tradesDao.getAll()
    }
    fun getSortedByStrategList(strategy: String):List<Trade>{
        return tradesDao.sortByStrategy(strategy)
    }
    fun getSortedByInstrumenList(instrument: String):List<Trade>{
        return tradesDao.sortByInstrument(instrument)
    }
    fun getShortPos(): Int {
        return tradesDao.countTradesByDirection(Directions.Short.name)
    }

    fun getLongPos(): Int {
        return  tradesDao.countTradesByDirection(Directions.Long.name)
    }

    fun getWinNumber(): Int {
        return tradesDao.countTradesByResult(Results.Victory.name)
    }

    fun getDefNumber(): Int {
        return  tradesDao.countTradesByResult(Results.Defeat.name)
    }

    fun getDayStatistic(day:DaysOfWeek): List<Trade> {
        return  tradesDao.getTradesByDay(day.name)
    }
    fun countTradesByResultAndDate(results: Results,day:DaysOfWeek): Int {
        return  tradesDao.countTradesByResultAndDate(results.name,day.name)
    }


    //Strategy part


    fun readStrategies(): List<Strategy> {
        return strategiesDao.getAllStrategies()
    }

    fun addStrategy(strategy: Strategy) {
        strategiesDao.insertStrategy(strategy)
    }

    fun deleteStrategy(strategy: String) {
        strategiesDao.deleteStrategyByName(strategy)
    }



    //instrument part


    fun readInstruments(): List<Instrument> {
        return instrumentsDao.getAllInstruments()
    }

    fun addInstrument(instrument: Instrument) {
        instrumentsDao.insertInstrument(instrument)
    }

    fun deleteInstrument(instrument: String) {
        instrumentsDao.deleteInstrumentByName(instrument)
    }
}