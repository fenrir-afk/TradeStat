package com.example.domain.util

import com.example.domain.model.Directions
import com.example.domain.model.Trade


/**
*In this class we are getting a rating of each instrument or strategy and
* count short and long number of trades of instruments and strategies
*  @param [names] is a list of Instruments or strategies
 * @param [winTrades] is a list all trades with result: Victory
 * @param [defeatTrades] is a list all trades with result: Defeat
 * @param [token] is an int value which is used to change the instrument count logic to strategy
* */
class RatingCounter(
    val names: List<String>,
    private val winTrades: List<Trade>,
    private val defeatTrades: List<Trade>,
    private val token: Int
) {


    var winRateList = mutableListOf<Int>()
    var shortWinRateList = mutableListOf<Int>()
    var longWinRateList = mutableListOf<Int>()

    var tradeNumbers = mutableListOf<Int>()
    var tradeShortNumbers = mutableListOf<Int>()
    var tradeLongNumbers = mutableListOf<Int>()
    init {
        updateData()
    }
    /**
    * Updates rating lists with new rating data and number of longs and shorts
    * */
    fun updateData(){
         winRateList = mutableListOf()
         shortWinRateList = mutableListOf()
         longWinRateList = mutableListOf()
         tradeNumbers = mutableListOf()
         tradeShortNumbers = mutableListOf()
         tradeLongNumbers = mutableListOf()
         names.forEach label@{
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
                    if (token == 2){
                        if (it == trade.strategy){
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
                    if (token == 2){
                        if (it == trade.strategy){
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
                tradeNumbers.add(wins+defeats)
                tradeShortNumbers.add(shortWins+shortDefeats)
                tradeLongNumbers.add(longDefeats+longWins)
                if (wins + defeats != 0){
                    winRateList.add(
                        (wins*100)/(wins + defeats)
                    )
                }else{
                    winRateList.add(0)
                    longWinRateList.add(0)
                    shortWinRateList.add(0)
                    return@label
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