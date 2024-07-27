package com.example.data.instrument.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.instrument.locale.entity.Instrument

@Dao
interface InstrumentDao {
    @Query("SELECT * FROM insturment_table")
    fun getAllInstruments(): List<Instrument>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInstrument(vararg instrument: Instrument)
    @Query("DELETE FROM insturment_table WHERE instrument_name=:insName")
    fun deleteInstrumentByName(insName: String)
}