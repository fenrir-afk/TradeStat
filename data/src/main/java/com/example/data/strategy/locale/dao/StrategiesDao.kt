package com.example.data.strategy.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.strategy.locale.entity.Strategy

@Dao
interface StrategiesDao {
    @Query("SELECT * FROM strategy_table")
    fun getAllStrategies(): List<Strategy>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStrategy(vararg strategy: Strategy)
    @Query("DELETE FROM strategy_table WHERE strategy_name=:strName")
    fun deleteStrategyByName(strName: String)


}