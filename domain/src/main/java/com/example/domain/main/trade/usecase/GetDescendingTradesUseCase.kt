package com.example.domain.main.trade.usecase


import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Trade

class GetDescendingTradesUseCase(val rep: TradeRepository) {
    fun execute(): List<Trade> {
        return rep.getTradesSortedByDateDescending()
    }
}