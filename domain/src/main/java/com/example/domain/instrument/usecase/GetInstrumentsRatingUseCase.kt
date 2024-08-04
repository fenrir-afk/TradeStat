package com.example.domain.instrument.usecase

import com.example.domain.contracts.InstrumentRepository
import com.example.domain.contracts.StrategyRepository
import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Results
import com.example.domain.util.RatingCounter

class GetInstrumentsRatingUseCase (private val tradeRepository: TradeRepository, private val instrumentRepository: InstrumentRepository) {
    fun execute(): RatingCounter {
        val winTrades = tradeRepository.getTradesByResult(Results.Victory.name)
        val defeatTrades = tradeRepository.getTradesByResult(Results.Defeat.name)
        val instrumentsNames = instrumentRepository.getAllInstruments().map { it.instrumentName }
        val ratingObj = RatingCounter(instrumentsNames, winTrades, defeatTrades, 1)
        return ratingObj
    }
}