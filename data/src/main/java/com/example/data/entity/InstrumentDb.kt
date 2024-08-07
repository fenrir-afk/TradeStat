package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
/**
 * @param id id for the strategy
 * @param instrumentName the name of the instrument
 * */
@Entity(tableName = "insturment_table",indices = [Index(value = arrayOf("instrument_name"), unique = true)])
data class InstrumentDb(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "instrument_name") val instrumentName: String
)