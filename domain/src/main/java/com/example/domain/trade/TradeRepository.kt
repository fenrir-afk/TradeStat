package com.example.domain.trade

interface TradeRepository {
   fun getNumberByResult(result:String):Int
    fun getNumberByDirection(direction:String):Int
}