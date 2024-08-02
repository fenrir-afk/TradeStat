package com.example.domain.main.trade.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Strategy

class GetAllStrategiesUseCase(val rep: TradeRepository) {
    fun execute(): List<Strategy> {
        return rep.getAllStrategies()
    }
}