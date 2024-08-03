package com.example.data.strategy.dataSource

import com.example.data.mapper.toDbData
import com.example.data.mapper.toDomain
import com.example.data.strategy.dao.StrategiesDao
import com.example.domain.model.Strategy
import javax.inject.Inject

class StrategyDataSourceImp @Inject constructor(private val dao:StrategiesDao):StrategyDataSource {
    override fun getAllStrategies(): List<Strategy> {
        return dao.getAllStrategies().map { it.toDomain() }
    }

    override fun addStrategy(strategy: Strategy) {
       dao.insertStrategy(strategy.toDbData())
    }

    override fun deleteStrategy(name: String) {
        dao.deleteStrategyByName(name)
    }
}