package com.example.domain.main.trade.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Instrument

class GetAllInstrumentsUseCase(val rep: TradeRepository) {
    fun execute(): List<Instrument> {
        return rep.getAllInstruments()
    }
}