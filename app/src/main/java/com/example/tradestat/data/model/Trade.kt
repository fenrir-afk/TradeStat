package com.example.tradestat.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trade_table")
data class Trade(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "trade_direction") val tradeDirection: String,
    @ColumnInfo(name = "trade_date") val tradeDate: String,
    @ColumnInfo(name = "trade_strategy") val strategy: String,
    @ColumnInfo(name = "trade_result") val tradeResult: String,
    @ColumnInfo(name = "Instrument") val instrument: String,
)
