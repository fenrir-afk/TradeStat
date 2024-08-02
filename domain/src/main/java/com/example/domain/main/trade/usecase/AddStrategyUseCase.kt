package com.example.domain.main.trade.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Strategy

class AddStrategyUseCase(val rep: TradeRepository) {
    fun execute(strategy: Strategy){
        rep.addStrategy(strategy)
    }
}