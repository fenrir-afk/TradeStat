package com.example.domain.strategy.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Trade

class GetAllTradesDescendingUseCase(private val rep: TradeRepository) {
    fun execute(): List<Trade> {
        return rep.getTradesSortedByDateDescending()
    }
}