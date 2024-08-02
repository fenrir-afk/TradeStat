package com.example.data.instrument.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entity.InstrumentDb

@Dao
interface InstrumentDao {
    @Query("SELECT * FROM insturment_table")
    fun getAllInstruments(): List<InstrumentDb>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInstrument(vararg instrument: InstrumentDb)
    @Query("DELETE FROM insturment_table WHERE instrument_name=:insName")
    fun deleteInstrumentByName(insName: String)
}