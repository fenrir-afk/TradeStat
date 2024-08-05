package com.example.domain.main.trade.usecase

import com.example.domain.contracts.StrategyRepository
import com.example.domain.model.Strategy

class AddStrategyUseCase(val rep: StrategyRepository) {
    fun execute(strategy: Strategy){
        rep.addStrategy(strategy)
    }
}