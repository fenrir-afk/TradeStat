package com.example.data.trade.repository

import com.example.data.mapper.toDomain
import com.example.data.trade.dataSource.TradeDataSource
import com.example.domain.contracts.TradeRepository
import com.example.domain.model.DaysOfWeek
import com.example.domain.model.Results
import com.example.domain.model.Trade
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TradeRepositoryImp @Inject constructor(private val dataSource:TradeDataSource): TradeRepository {
    override fun addTrade(trade: Trade) {
        dataSource.addTrade(trade)
    }

    override fun deleteTrade(trade: Trade) {
        dataSource.deleteTrade(trade)
    }

    override fun getTradesSortedByDateDescending(): List<Trade> {
        return  dataSource.getTradesSortedByDateDescending()
    }

    override fun getTradesSortedByDateAscending(): List<Trade> {
        return  dataSource.getTradesSortedByDateAscending()
    }

    override fun getSortedByStrategiesList(strategy: String): List<Trade> {
       return dataSource.getSortedByStrategiesList(strategy)
    }

    override fun getSortedByInstrumentList(instrument: String): List<Trade> {
        return dataSource.getSortedByInstrumentList(instrument)
    }

    override fun getTradesByResult(result: String): List<Trade> {
        return dataSource.getTradesByResult(result)
    }

    override fun getShortPos(): Int {
        return dataSource.getShortPos()
    }

    override fun getLongPos(): Int {
        return dataSource.getLongPos()
    }

    override fun getWinNumber(): Int {
        return dataSource.getWinNumber()
    }

    override fun getDefNumber(): Int {
        return dataSource.getWinNumber()
    }

    override fun getDayStatistic(): Flow<List<Trade>> {
        return dataSource.getDayStatistic().map {list ->  list.map {item -> item.toDomain() } }
    }

    override fun countTradesByResultAndDate(results: Results, day: DaysOfWeek): Int {
        return dataSource.countTradesByResultAndDate(results,day)
    }

}