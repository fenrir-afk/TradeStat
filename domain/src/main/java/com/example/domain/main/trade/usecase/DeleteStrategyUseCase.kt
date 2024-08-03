package com.example.domain.main.trade.usecase

import com.example.domain.contracts.StrategyRepository

class DeleteStrategyUseCase(val rep: StrategyRepository) {
    fun execute(name: String){
        rep.deleteStrategy(name)
    }
}