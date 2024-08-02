package com.example.domain.contracts

import com.example.domain.model.Instrument

interface InstrumentRepository {
    fun getAllInstruments(): List<Instrument>

    fun addInstrument(instrument: Instrument)
    fun deleteInstrument(name: String)
}