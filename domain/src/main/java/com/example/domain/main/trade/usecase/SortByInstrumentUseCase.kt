package com.example.domain.main.trade.usecase


import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Trade

class SortByInstrumentUseCase(val rep: TradeRepository) {
    fun execute(instrument:String): List<Trade> {
        return rep.getSortedByInstrumentList(instrument)
    }
}