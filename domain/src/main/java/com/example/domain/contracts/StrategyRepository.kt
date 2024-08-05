package com.example.domain.contracts

import com.example.domain.model.Strategy

interface StrategyRepository {
    fun getAllStrategies(): List<Strategy>
    fun addStrategy(strategy: Strategy)
    fun deleteStrategy(name: String)
}