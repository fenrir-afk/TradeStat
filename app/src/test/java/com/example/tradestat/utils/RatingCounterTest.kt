package com.example.tradestat.utils

import com.example.domain.model.Trade
import com.example.domain.util.RatingCounter
import org.junit.Before
import org.junit.Test

class RatingCounterTest{
    private lateinit var ratingCounter: RatingCounter
    private lateinit var strategies: MutableList<String>
    private lateinit var instruments: MutableList<String>
    private lateinit var  winTrades: MutableList<Trade>
    private lateinit var defeatTrades: MutableList<Trade>
    @Before
    fun setUp(){
          strategies = mutableListOf(
             "Strategy1",
             "Strategy2"
         )
         instruments = mutableListOf(
            "Instrument1",
            "Instrument2"
        )
         winTrades = mutableListOf(
             Trade(0,"Short","Date","Strategy1","Victory","Instrument1","Date",""),
             Trade(1,"Long","Date","Strategy1","Victory","Instrument1","Date",""),
             Trade(2,"Long","Date","Strategy1","Victory","Instrument1","Date",""),
             Trade(3,"Long","Date","Strategy2","Victory","Instrument1","Date",""),
             Trade(4,"Long","Date","Strategy1","Victory","Instrument2","Date",""),
             Trade(5,"Short","Date","Strategy2","Victory","Instrument1","Date",""),
             )
         defeatTrades = mutableListOf(
             Trade(6,"Short","Date","Strategy2","Defeat","Instrument2","Date",""),
             Trade(7,"Long","Date","Strategy2","Defeat","Instrument2","Date",""),
             Trade(8,"Short","Date","Strategy1","Defeat","Instrument1","Date",""),
             Trade(9,"Long","Date","Strategy1","Defeat","Instrument1","Date",""),
             Trade(10,"Long","Date","Strategy1","Defeat","Instrument2","Date",""),
             Trade(11,"Short","Date","Strategy2","Defeat","Instrument1","Date",""),
         )

    }
    // Strategies test
    @Test
    fun getStrategiesRatingTest(){
        ratingCounter = RatingCounter(strategies,winTrades,defeatTrades,2)
        assert(ratingCounter.winRateList[0] == 57 && ratingCounter.winRateList[1] == 40)
    }
    @Test
    fun getStrategiesWinRateListShortTest(){
        ratingCounter = RatingCounter(strategies,winTrades,defeatTrades,2)
        assert(ratingCounter.shortWinRateList[0] == 50 && ratingCounter.shortWinRateList[1] == 33)
    }
    @Test
    fun getStrategiesWinRateListLongTest(){
        ratingCounter = RatingCounter(strategies,winTrades,defeatTrades,2)
        assert(ratingCounter.longWinRateList[0] == 60 && ratingCounter.longWinRateList[1] == 50)
    }
    @Test
    fun getStrategiesTradeNumberTest(){
        ratingCounter = RatingCounter(strategies,winTrades,defeatTrades,2)
        assert(ratingCounter.tradeNumbers[0] == 7 && ratingCounter.tradeNumbers[1] == 5)
    }
    @Test
    fun getStrategiesShortTradeNumberTest(){
        ratingCounter = RatingCounter(strategies,winTrades,defeatTrades,2)
        assert(ratingCounter.tradeShortNumbers[0] == 2 && ratingCounter.tradeShortNumbers[1] == 3)
    }
    @Test
    fun getStrategiesLongTradeNumberTest(){
        ratingCounter = RatingCounter(strategies,winTrades,defeatTrades,2)
        assert(ratingCounter.tradeLongNumbers[0] == 5 && ratingCounter.tradeLongNumbers[1] == 2)
    }
    // Instruments test
    @Test
    fun getInstrumentsRatingTest(){
        ratingCounter = RatingCounter(instruments,winTrades,defeatTrades,1)
        assert(ratingCounter.winRateList[0] == 62 && ratingCounter.winRateList[1] == 25)
    }
    @Test
    fun getInstrumentsWinRateListShortTest(){
        ratingCounter = RatingCounter(instruments,winTrades,defeatTrades,1)
        assert(ratingCounter.shortWinRateList[0] == 50 && ratingCounter.shortWinRateList[1] == 0)
    }
    @Test
    fun getInstrumentsWinRateListLongTest(){
        ratingCounter = RatingCounter(instruments,winTrades,defeatTrades,1)
        assert(ratingCounter.longWinRateList[0] == 75 && ratingCounter.longWinRateList[1] == 33)
    }
    @Test
    fun getInstrumentsTradeNumberTest(){
        ratingCounter = RatingCounter(instruments,winTrades,defeatTrades,1)
        assert(ratingCounter.tradeNumbers[0] == 8 && ratingCounter.tradeNumbers[1] == 4)
    }
    @Test
    fun getInstrumentsShortTradeNumberTest(){
        ratingCounter = RatingCounter(instruments,winTrades,defeatTrades,1)
        assert(ratingCounter.tradeShortNumbers[0] == 4 && ratingCounter.tradeShortNumbers[1] == 1)
    }
    @Test
    fun getInstrumentsLongTradeNumberTest(){
        ratingCounter = RatingCounter(instruments,winTrades,defeatTrades,1)
        assert(ratingCounter.tradeLongNumbers[0] == 4 && ratingCounter.tradeLongNumbers[1] == 3)
    }
}