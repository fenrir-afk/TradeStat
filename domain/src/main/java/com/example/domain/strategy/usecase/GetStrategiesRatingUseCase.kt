package com.example.domain.strategy.usecase

import com.example.domain.contracts.StrategyRepository
import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Results
import com.example.domain.util.RatingCounter

class GetStrategiesRatingUseCase (private val tradeRepository: TradeRepository,private val strategyRepository: StrategyRepository) {
    fun execute(): RatingCounter {
        val winTrades = tradeRepository.getTradesByResult(Results.Victory.name)
        val defeatTrades = tradeRepository.getTradesByResult(Results.Defeat.name)
        val strategiesNames = strategyRepository.getAllStrategies().map { it.strategyName }
        val ratingObj = RatingCounter(strategiesNames, winTrades, defeatTrades, 2)
        return ratingObj
    }
}
