package com.example.data.trade.dataSource

import com.example.data.entity.TradeDb
import com.example.domain.model.DaysOfWeek
import com.example.domain.model.Results
import com.example.domain.model.Trade
import kotlinx.coroutines.flow.Flow

interface TradeDataSource {
    fun addTrade(trade: Trade)
    fun deleteTrade(trade: Trade)
    fun getTradesSortedByDateDescending():List<Trade>
    fun getTradesSortedByDateAscending():List<Trade>
    fun getSortedByStrategiesList(strategy: String):List<Trade>
    fun getSortedByInstrumentList(instrument: String):List<Trade>
    fun getTradesByResult(result: String): List<Trade>
    fun getShortPos(): Int
    fun getLongPos(): Int
    fun getWinNumber(): Int
    fun getDefNumber(): Int
    fun getDayStatistic(): Flow<List<TradeDb>>
    fun countTradesByResultAndDate(results: Results, day: DaysOfWeek): Int
}