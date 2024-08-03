package com.example.domain.main.trade.usecase

import com.example.domain.contracts.InstrumentRepository

class DeleteInstrumentUseCase(val rep: InstrumentRepository) {
    fun execute(name: String){
        rep.deleteInstrument(name)
    }
}