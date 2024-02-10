package com.example.tradestat.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "insturment_table")
data class Instrument(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "instrument_name") val instrumentName: String
)