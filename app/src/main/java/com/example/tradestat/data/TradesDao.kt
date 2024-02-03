package com.example.tradestat.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tradestat.data.model.Trade
@Dao
interface TradesDao {
    @Query("SELECT * FROM trade_table")
    fun getAll(): LiveData<List<Trade>>
    @Insert
    fun insert(vararg trades: Trade)


    @Delete
    fun delete(trades: Trade)
}