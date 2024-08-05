package com.example.domain.analysis.usecase

import com.example.domain.contracts.InstrumentRepository
import com.example.domain.contracts.StrategyRepository
import com.example.domain.contracts.TradeRepository
import com.example.domain.model.Results
import com.example.domain.util.RatingCounter

class GetMaxNameUseCase (
    private val tradeRepository: TradeRepository,
    private val strategyRepository: StrategyRepository,
    private val instrumentRepository: InstrumentRepository
    ) {
    fun execute(token:Int): String {
        val winTrades = tradeRepository.getTradesByResult(Results.Victory.toString())
        val defeatTrades = tradeRepository.getTradesByResult(Results.Defeat.toString())
        val names = mutableListOf<String>()
        if (token == 1){
            val list =  instrumentRepository.getAllInstruments()
            list.forEach{
                names.add(it.instrumentName)
            }
        }else{
            val list =  strategyRepository.getAllStrategies()
            list.forEach{
                names.add(it.strategyName)
            }
        }
        val obj = RatingCounter(names,winTrades,defeatTrades,token) //instruments
        val maxRate = obj.winRateList.max()
        var maxIndex = 0
        obj.winRateList.forEachIndexed { index, i -> //looking for index of the max value
            if (i == maxRate){
                maxIndex = index
            }
        }
        return names[maxIndex]
    }
}