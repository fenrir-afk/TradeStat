package com.example.tradestat.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade
@Dao
interface TradesDao {
    @Query("SELECT * FROM trade_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Trade>>


    @Query("SELECT * FROM trade_table ")
    fun sortByDate(): List<Trade>
    @Query("SELECT * FROM trade_table WHERE trade_strategy == :strategy ORDER BY id DESC")
    fun sortByStrategy(strategy:String): List<Trade>
    @Query("SELECT * FROM trade_table WHERE Instrument == :instrument ORDER BY id DESC")
    fun sortByInstrument(instrument:String): List<Trade>


    @Query("SELECT COUNT(*) FROM trade_table WHERE trade_direction = 'Short'")
    fun getCountShortTrades(): Int
    @Query("SELECT COUNT(*) FROM trade_table WHERE trade_direction = 'Long'")
    fun getCountLongTrades(): Int


    @Query("SELECT COUNT(*) FROM trade_table WHERE trade_result = 'Victory'")
    fun getCountWinTrades(): Int
    @Query("SELECT COUNT(*) FROM trade_table WHERE trade_result = 'Defeat'")
    fun getCountDefTrades(): Int


    @Insert
    fun insert(vararg trades: Trade)
    @Delete
    fun delete(trade: Trade)
}