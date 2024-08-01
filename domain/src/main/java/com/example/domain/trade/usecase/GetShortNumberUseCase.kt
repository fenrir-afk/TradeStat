package com.example.domain.trade.usecase

import com.example.domain.trade.TradeRepository
import com.example.domain.util.Directions

class GetShortNumberUseCase(val rep:TradeRepository) {
    fun execute(): Int{
        return rep.getNumberByDirection(Directions.Short.toString())
    }
}