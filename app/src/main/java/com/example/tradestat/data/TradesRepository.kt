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
    fun getTradesSortedByDateDescending():List<Trade>{
        return tradesDao.sortByDate()
    }
    fun getTradesSortedByDateAscending():List<Trade>{
        return tradesDao.getAll()
    }
    fun getSortedByStrategiesList(strategy: String):List<Trade>{
        return tradesDao.sortByStrategy(strategy)
    }


    fun getTradesByResult(result: String): List<Trade> {
        return tradesDao.getTradesByResult(result)
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


    fun getAllStrategies(): List<Strategy> {
        return strategiesDao.getAllStrategies()
    }

    fun addStrategy(strategy: Strategy) {
        strategiesDao.insertStrategy(strategy)
    }
    fun deleteStrategy(name: String) {
        strategiesDao.deleteStrategyByName(name)
    }



    //instrument part


    fun getAllInstruments(): List<Instrument> {
        return instrumentsDao.getAllInstruments()
    }

    fun addInstrument(instrument: Instrument) {
        instrumentsDao.insertInstrument(instrument)
    }
    fun deleteInstrument(name: String) {
        instrumentsDao.deleteInstrumentByName(name)
    }




}