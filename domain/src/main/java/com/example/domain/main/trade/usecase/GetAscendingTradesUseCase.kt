package com.example.domain.main.trade.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Trade

class GetAscendingTradesUseCase (val rep: TradeRepository) {
    fun execute(): List<Trade> {
        return rep.getTradesSortedByDateAscending()
    }
}