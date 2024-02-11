package com.example.tradestat.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "strategy_table", indices = [Index(value = arrayOf("strategy_name"), unique = true)])
data class Strategy(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "strategy_name") val strategyName: String
)