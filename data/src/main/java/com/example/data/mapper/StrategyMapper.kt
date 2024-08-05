package com.example.data.mapper

import com.example.data.entity.StrategyDb
import com.example.domain.model.Strategy


fun Strategy.toDbData() = StrategyDb(
    id = id,
    strategyName = strategyName
)
fun StrategyDb.toDomain() = Strategy(
    id = id,
    strategyName = strategyName
)

