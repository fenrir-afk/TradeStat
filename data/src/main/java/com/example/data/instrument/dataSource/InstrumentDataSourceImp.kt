package com.example.data.instrument.dataSource

import com.example.data.instrument.dao.InstrumentDao
import com.example.data.mapper.toDbData
import com.example.data.mapper.toDomain
import com.example.domain.model.Instrument
import javax.inject.Inject

class InstrumentDataSourceImp @Inject constructor(private val dao: InstrumentDao):InstrumentDataSource {
    override fun getAllInstruments(): List<Instrument> {
       return dao.getAllInstruments().map { it.toDomain() }
    }

    override fun addInstrument(instrument: Instrument) {
       dao.insertInstrument(instrument.toDbData())
    }

    override fun deleteInstrument(name: String) {
       dao.deleteInstrumentByName(name)
    }
}