package com.example.data.mapper

import com.example.data.entity.InstrumentDb
import com.example.domain.model.Instrument

fun Instrument.toDbData() = InstrumentDb(
    id = id,
    instrumentName = instrumentName
)
fun InstrumentDb.toDomain() = Instrument(
    id = id,
    instrumentName = instrumentName
)
