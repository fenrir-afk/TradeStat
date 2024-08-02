package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
/**
 * @param id id for the strategy
 * @param strategyName the name of the strategy
 * */
@Entity(tableName = "strategy_table", indices = [Index(value = arrayOf("strategy_name"), unique = true)])
data class StrategyDb(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "strategy_name") val strategyName: String
)
