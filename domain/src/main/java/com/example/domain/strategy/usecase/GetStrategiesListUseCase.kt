package com.example.domain.strategy.usecase

import com.example.domain.contracts.StrategyRepository
import com.example.domain.model.Strategy

class GetStrategiesListUseCase (private val rep: StrategyRepository) {
    fun execute(): List<Strategy> {
        return rep.getAllStrategies()
    }
}