package com.example.domain.main.trade.usecase

import com.example.domain.contracts.InstrumentRepository
import com.example.domain.model.Instrument

class GetAllInstrumentsUseCase(val rep: InstrumentRepository) {
    fun execute(): List<Instrument> {
        return rep.getAllInstruments()
    }
}