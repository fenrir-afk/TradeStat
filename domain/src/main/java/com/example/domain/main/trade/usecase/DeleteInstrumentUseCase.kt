package com.example.domain.main.trade.usecase

import com.example.domain.contracts.TradeRepository

class DeleteInstrumentUseCase(val rep: TradeRepository) {
    fun execute(name: String){
        rep.deleteInstrument(name)
    }
}