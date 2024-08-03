package com.example.domain.main.trade.usecase


import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Trade

class AddTradeUseCase(val rep: TradeRepository) {
    fun execute(trade: Trade){
        rep.addTrade(trade)
    }
}