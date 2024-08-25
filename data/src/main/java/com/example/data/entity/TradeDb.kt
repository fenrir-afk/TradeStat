package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.util.Converters

/**
 * @param tradeDirection the direction of the trade
 * @param tradeDate the day when the position was opened
 * @param strategy the name of strategy
 * @param tradeResult the result of the trade Victory/Defeat
 * @param instrument the name of instrument on which trade was done
 * @param ADDate the day when the trade was added to the app
 * @param description description for the trade
 * */
@TypeConverters(Converters::class)
@Entity(tableName = "trade_table")
data class TradeDb(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "trade_direction") val tradeDirection: String,
    @ColumnInfo(name = "trade_date") val tradeDate: String,//day of position opening
    @ColumnInfo(name = "trade_strategy") val strategy: String,
    @ColumnInfo(name = "trade_result") val tradeResult: String,
    @ColumnInfo(name = "Instrument") val instrument: String,
    @ColumnInfo(name = "add_date") val ADDate: String, //date of adding to the app
    @ColumnInfo(name = "description") val description: String
)
