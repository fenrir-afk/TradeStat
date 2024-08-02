package com.example.domain.main.trade.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Instrument

class AddInstrumentUseCase(val rep: TradeRepository) {
    fun execute(instrument: Instrument){
        rep.addInstrument(instrument)
    }
}