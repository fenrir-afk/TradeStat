package com.example.data.strategy.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entity.StrategyDb

@Dao
interface StrategiesDao {
    @Query("SELECT * FROM strategy_table")
    fun getAllStrategies(): List<StrategyDb>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStrategy(vararg strategy: StrategyDb)
    @Query("DELETE FROM strategy_table WHERE strategy_name=:strName")
    fun deleteStrategyByName(strName: String)

}