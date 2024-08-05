package com.example.data.instrument.dataSource

import com.example.domain.model.Instrument

interface InstrumentDataSource {
    fun getAllInstruments(): List<Instrument>

    fun addInstrument(instrument: Instrument)
    fun deleteInstrument(name: String)
}