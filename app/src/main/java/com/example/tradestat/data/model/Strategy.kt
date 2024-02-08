package com.example.tradestat.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "strategy_table")
data class Strategy(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "strategy_name") val strategyName: String
)
