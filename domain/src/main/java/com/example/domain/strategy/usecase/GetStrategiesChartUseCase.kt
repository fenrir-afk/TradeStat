package com.example.domain.strategy.usecase

import com.example.domain.contracts.StrategyRepository
import com.example.domain.contracts.TradeRepository
import com.example.domain.model.ChartEntry
import com.example.domain.model.Results

class GetStrategiesChartUseCase (private val tradeRep: TradeRepository,private val strategyRep:StrategyRepository) {
    fun execute(): MutableList<List<ChartEntry>> {
            val strategies = strategyRep.getAllStrategies()
            val trades = tradeRep.getTradesSortedByDateDescending()
            val strategyLists = mutableListOf<List<ChartEntry>>()
            for (i in strategies.indices){
                val entryList = mutableListOf<ChartEntry>()
                entryList.add(ChartEntry(0f, 0f))
                var counter1 = 0f
                var counter2 = 0f
                for (b in trades.indices){
                    if (trades[b].strategy == strategies[i].strategyName){
                        counter1++
                        if (trades[b].tradeResult == Results.Victory.name){
                            counter2++
                        }else{
                            counter2--
                        }
                        entryList.add(ChartEntry(counter1, counter2))
                    }
                }
                strategyLists.add(entryList)
            }
           return strategyLists
        }
    }
