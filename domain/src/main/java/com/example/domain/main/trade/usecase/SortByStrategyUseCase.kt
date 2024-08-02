package com.example.domain.main.trade.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Trade

class SortByStrategyUseCase(val rep: TradeRepository) {
    fun execute(strategy:String): List<Trade> {
        return rep.getSortedByStrategiesList(strategy)
    }
}