package com.example.domain.strategy.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Results
import com.example.domain.model.Trade

class GetDefeatTradesUseCase(private val rep: TradeRepository) {
    fun execute(): List<Trade> {
        return rep.getTradesByResult(Results.Defeat.toString())
    }
}