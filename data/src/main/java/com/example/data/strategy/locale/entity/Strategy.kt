package com.example.data.strategy.locale.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
/**
 * @param id id for the strategy
 * @param strategyName the name of the strategy
 * */
@Entity(tableName = "strategy_table", indices = [Index(value = arrayOf("strategy_name"), unique = true)])
data class Strategy(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "strategy_name") val strategyName: String
)
