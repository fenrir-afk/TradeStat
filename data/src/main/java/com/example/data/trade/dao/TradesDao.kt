package com.example.data.trade.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.data.entity.TradeDb
@Dao
interface TradesDao {
    @Query("SELECT * FROM trade_table ORDER BY id DESC")
    fun getAll(): List<TradeDb>


    @Query("SELECT * FROM trade_table ")
    fun sortByDate(): List<TradeDb>
    @Query("SELECT * FROM trade_table WHERE trade_strategy == :strategy ORDER BY id DESC")
    fun sortByStrategy(strategy:String): List<TradeDb>
    @Query("SELECT * FROM trade_table WHERE Instrument == :instrument ORDER BY id DESC")
    fun sortByInstrument(instrument:String): List<TradeDb>


    @Query("SELECT COUNT(*) FROM trade_table WHERE trade_direction = :direction")
    fun countTradesByDirection(direction:String): Int

    @Query("SELECT COUNT(*) FROM trade_table WHERE trade_result = :result")
    fun countTradesByResult(result:String): Int
    @Query("SELECT COUNT(*) FROM trade_table WHERE trade_result = :result and trade_date = :date")
    fun countTradesByResultAndDate(result:String,date:String): Int

    @Query("SELECT COUNT(*) FROM trade_table WHERE trade_result = :result and Instrument = :instrument")
    fun countTradesByResultAndInstrument(result:String,instrument:String): Int




    @Query("SELECT * FROM trade_table WHERE trade_date == :dayOfWeek")
    fun getTradesByDay(dayOfWeek: String): List<TradeDb>




    @Query("SELECT * FROM trade_table WHERE trade_result == :result")
    fun getTradesByResult(result: String): List<TradeDb>



    @Insert
    fun insert(vararg trades: TradeDb)
    @Delete
    fun delete(trade: TradeDb)
}