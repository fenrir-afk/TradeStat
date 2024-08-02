package com.example.data.trade.dataSource

import com.example.data.trade.dao.TradesDao
import com.example.data.mapper.toDbData
import com.example.data.mapper.toDomain
import com.example.domain.model.DaysOfWeek
import com.example.domain.model.Directions
import com.example.domain.model.Instrument
import com.example.domain.model.Results
import com.example.domain.model.Strategy
import com.example.domain.model.Trade
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TradeDataSourceImp @Inject constructor(private val dao: TradesDao): TradeDataSource {
    override fun addTrade(trade: Trade) {
        dao.insert(trade.toDbData())
    }

    override fun deleteTrade(trade: Trade) {
        dao.delete(trade.toDbData())
    }

    override fun getTradesSortedByDateDescending(): List<Trade> {
       return dao.sortByDate().map { it.toDomain() }
    }

    override fun getTradesSortedByDateAscending(): List<Trade> {
        return dao.getAll().map { it.toDomain() }
    }

    override fun getSortedByStrategiesList(strategy: String): List<Trade> {
        return dao.sortByStrategy(strategy).map { it.toDomain() }
    }

    override fun getSortedByInstrumentList(instrument: String): List<Trade> {
        return dao.sortByInstrument(instrument).map { it.toDomain() }
    }

    override fun getTradesByResult(result: String): List<Trade> {
        return dao.getTradesByResult(result).map { it.toDomain() }
    }

    override fun getShortPos(): Int {
        return dao.countTradesByDirection(Directions.Short.toString())
    }

    override fun getLongPos(): Int {
        return dao.countTradesByDirection(Directions.Long.toString())
    }

    override fun getWinNumber(): Int {
        return dao.countTradesByResult(Results.Victory.toString())
    }

    override fun getDefNumber(): Int {
        return dao.countTradesByResult(Results.Defeat.toString())
    }

    override fun getDayStatistic() = flow {
        DaysOfWeek.entries.forEach{
            emit(dao.getTradesByDay(it.toString()))
        }
    }
    override fun countTradesByResultAndDate(results: Results, day: DaysOfWeek): Int {
        return dao.countTradesByResultAndDate(results.toString(),day.toString())
    }

}