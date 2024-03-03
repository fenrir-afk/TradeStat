package com.example.tradestat.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade
import java.time.DayOfWeek

@Dao
interface TradesDao {
    @Query("SELECT * FROM trade_table ORDER BY id DESC")
    fun getAll(): List<Trade>


    @Query("SELECT * FROM trade_table ")
    fun sortByDate(): List<Trade>
    @Query("SELECT * FROM trade_table WHERE trade_strategy == :strategy ORDER BY id DESC")
    fun sortByStrategy(strategy:String): List<Trade>
    @Query("SELECT * FROM trade_table WHERE Instrument == :instrument ORDER BY id DESC")
    fun sortByInstrument(instrument:String): List<Trade>


    @Query("SELECT COUNT(*) FROM trade_table WHERE trade_direction = :direction")
    fun countTradesByDirection(direction:String): Int

    @Query("SELECT COUNT(*) FROM trade_table WHERE trade_result = :result")
    fun countTradesByResult(result:String): Int


    @Query("SELECT COUNT(*) FROM trade_table WHERE trade_result = :result and trade_date = :dayOfWeek")
    fun countTradesByDayAndResult(result:String,dayOfWeek: String): Int



    @Insert
    fun insert(vararg trades: Trade)
    @Delete
    fun delete(trade: Trade)
}