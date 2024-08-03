package com.example.data.instrument.repository

import com.example.data.instrument.dataSource.InstrumentDataSource
import com.example.domain.contracts.InstrumentRepository
import com.example.domain.model.Instrument
import javax.inject.Inject

class InstrumentRepositoryImp @Inject constructor(private val dataSource: InstrumentDataSource):InstrumentRepository {
    override fun getAllInstruments(): List<Instrument> {
        return dataSource.getAllInstruments()
    }

    override fun addInstrument(instrument: Instrument) {
        dataSource.addInstrument(instrument)
    }

    override fun deleteInstrument(name: String) {
        dataSource.deleteInstrument(name)
    }
}