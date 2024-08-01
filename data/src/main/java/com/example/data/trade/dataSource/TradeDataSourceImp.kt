package com.example.data.trade.dataSource

import com.example.data.trade.locale.dao.TradesDao
import javax.inject.Inject

class TradeDataSourceImp @Inject constructor(private val tradeDao: TradesDao):TradeDataSource {

    override fun getNumberByDirection(direction:String): Int {
       return tradeDao.countTradesByDirection(direction)
    }

    override fun getNumberByResult(result:String): Int {
       return tradeDao.countTradesByResult(result)
    }
}