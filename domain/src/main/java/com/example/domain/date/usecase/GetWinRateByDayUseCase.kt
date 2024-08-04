package com.example.domain.date.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.model.DaysOfWeek
import com.example.domain.model.Results

class GetWinRateByDayUseCase (private val tradeRep: TradeRepository) {
    fun execute(daysOfWeek:DaysOfWeek): Int {
        val victories  = tradeRep.countTradesByResultAndDate(Results.Victory,daysOfWeek)
        val defeats  = tradeRep.countTradesByResultAndDate(Results.Defeat,daysOfWeek)
        if (defeats != 0){
            return victories*100/(defeats + victories)
        }
        if (victories == 0){
            return 0 //0% win rate
        }
        return 100 //100% win rate
    }
}