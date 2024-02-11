package com.example.tradestat.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "insturment_table",indices = [Index(value = arrayOf("instrument_name"), unique = true)])
data class Instrument(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "instrument_name") val instrumentName: String
)