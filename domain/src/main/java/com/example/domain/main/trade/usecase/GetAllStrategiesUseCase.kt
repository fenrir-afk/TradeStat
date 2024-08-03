package com.example.domain.main.trade.usecase

import com.example.domain.contracts.StrategyRepository
import com.example.domain.model.Strategy

class GetAllStrategiesUseCase(val rep: StrategyRepository) {
    fun execute(): List<Strategy> {
        return rep.getAllStrategies()
    }
}