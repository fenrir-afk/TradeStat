package com.example.domain.main.trade.usecase

import com.example.domain.contracts.TradeRepository

class DeleteStrategyUseCase(val rep: TradeRepository) {
    fun execute(name: String){
        rep.deleteStrategy(name)
    }
}