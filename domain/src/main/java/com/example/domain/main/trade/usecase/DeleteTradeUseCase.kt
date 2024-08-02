package com.example.domain.main.trade.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Trade

class DeleteTradeUseCase(val rep: TradeRepository) {
    fun execute(trade: Trade):Boolean{
       return rep.deleteTrade(trade)
    }
}