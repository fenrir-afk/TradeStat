package com.example.data.trade.dataSource

interface TradeDataSource {
    fun getNumberByDirection(direction: String): Int
    fun getNumberByResult(result: String): Int
}