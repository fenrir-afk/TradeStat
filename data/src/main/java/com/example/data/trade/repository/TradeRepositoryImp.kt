package com.example.data.trade.repository

import com.example.data.trade.dataSource.TradeDataSource
import com.example.domain.trade.TradeRepository
import javax.inject.Inject

class TradeRepositoryImp @Inject constructor(val dataSource:TradeDataSource):TradeRepository {
    override fun getNumberByResult(result: String): Int {
        return dataSource.getNumberByResult(result)
    }

    override fun getNumberByDirection(direction: String): Int {
        return dataSource.getNumberByDirection(direction)
    }
}