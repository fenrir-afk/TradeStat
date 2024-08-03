package com.example.data.strategy.repository

import com.example.data.strategy.dataSource.StrategyDataSource
import com.example.domain.contracts.StrategyRepository
import com.example.domain.model.Strategy
import javax.inject.Inject

class StrategyRepositoryImp @Inject constructor(private val dataSource: StrategyDataSource):StrategyRepository {
    override fun getAllStrategies(): List<Strategy> {
        return dataSource.getAllStrategies()
    }

    override fun addStrategy(strategy: Strategy) {
        dataSource.addStrategy(strategy)
    }

    override fun deleteStrategy(name: String) {
       dataSource.deleteStrategy(name)
    }
}