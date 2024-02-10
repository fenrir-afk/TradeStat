package com.example.tradestat.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.Strategy

@Dao
interface InstrumentDao {
    @Query("SELECT * FROM insturment_table")
    fun getAllInstruments(): List<Instrument>
    @Insert
    fun insertInstrument(vararg instrument: Instrument)
    @Query("DELETE FROM insturment_table WHERE instrument_name=:insName")
    fun deleteInstrumentByName(insName: String)
}