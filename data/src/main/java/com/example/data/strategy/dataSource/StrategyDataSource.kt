package com.example.data.strategy.dataSource

import com.example.domain.model.Strategy

interface StrategyDataSource {
    fun getAllStrategies(): List<Strategy>
    fun addStrategy(strategy: Strategy)
    fun deleteStrategy(name: String)
}