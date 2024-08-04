package com.example.domain.date.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Trade
import kotlinx.coroutines.flow.Flow

class GetDayStatisticsUseCase(private val tradeRep: TradeRepository) {
    fun execute(): Flow<List<Trade>> {
        return  tradeRep.getDayStatistic()
    }
}