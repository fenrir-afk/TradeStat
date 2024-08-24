package com.example.data.mapper

import com.example.data.entity.TradeDb
import com.example.domain.model.Trade

fun Trade.toDbData() = TradeDb(
    id = id,
    tradeDirection = tradeDirection,
    tradeDate = tradeDate,
    strategy = strategy,
    tradeResult = tradeResult,
    instrument = instrument,
    ADDate = ADDate,
    description = description,
    images = images

)

fun TradeDb.toDomain() = Trade(
    id = id,
    tradeDirection = tradeDirection,
    tradeDate = tradeDate,
    strategy = strategy,
    tradeResult = tradeResult,
    instrument = instrument,
    ADDate = ADDate,
    description = description,
    images = images
)