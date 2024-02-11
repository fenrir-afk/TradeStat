package com.example.tradestat.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade

@Dao
interface StrategiesDao {
    @Query("SELECT * FROM strategy_table")
    fun getAllStrategies(): List<Strategy>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStrategy(vararg strategy: Strategy)
    @Query("DELETE FROM strategy_table WHERE strategy_name=:strName")
    fun deleteStrategyByName(strName: String)


}