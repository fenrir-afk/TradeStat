package com.example.tradestat.ui

import com.example.tradestat.data.model.Directions
import com.example.tradestat.data.model.Trade

class RatingCounter(
    private val instrumentsNames: MutableList<String>,
    private val winTrades: List<Trade>,
    private val defeatTrades: List<Trade>,
    private val token: Int
) {
    val winRateList = mutableListOf<Int>()
    val shortWinRateList = mutableListOf<Int>()
    val longWinRateList = mutableListOf<Int>()
    fun updateData(){
            instrumentsNames.forEach{
                var wins = 0
                var defeats = 0
                var shortWins = 0
                var shortDefeats = 0
                var longWins = 0
                var longDefeats = 0
                winTrades.forEach{trade: Trade ->
                    if (token == 1){
                        if (it == trade.instrument){
                            if (trade.tradeDirection == Directions.Long.name){
                                longWins ++
                            }
                            if (trade.tradeDirection == Directions.Short.name){
                                shortWins ++
                            }
                            wins++
                        }
                    }
                }
                defeatTrades.forEach{trade: Trade ->
                    if (token == 1){
                        if (it == trade.instrument){
                            if (trade.tradeDirection == Directions.Long.name){
                                longDefeats ++
                            }
                            if (trade.tradeDirection == Directions.Short.name){
                                shortDefeats ++
                            }
                            defeats++
                        }
                    }
                }
                if (wins + defeats != 0){
                    winRateList.add(
                        (wins*100)/(wins + defeats)
                    )
                }else{
                    winRateList.add(0)
                    longWinRateList.add(0)
                    shortWinRateList.add(0)
                }


                if (longWins + longDefeats != 0){
                    longWinRateList.add(
                        (longWins*100)/(longWins + longDefeats)
                    )
                }else{
                    longWinRateList.add(0)

                }

                if (shortDefeats + shortWins != 0){
                    shortWinRateList.add(
                        (shortWins*100)/(shortDefeats + shortWins)
                    )
                }else{
                    shortWinRateList.add(0)

                }
            }
    }
}