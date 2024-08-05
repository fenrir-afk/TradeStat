package com.example.domain.main.trade.usecase

import com.example.domain.contracts.InstrumentRepository
import com.example.domain.model.Instrument

class AddInstrumentUseCase(val rep: InstrumentRepository) {
    fun execute(instrument: Instrument){
        rep.addInstrument(instrument)
    }
}