package com.example.domain.results.usecase

import com.example.domain.model.Results
import com.example.domain.model.Trade

/**
 * In this method we are getting rating for the single instrument of strategy
 * list  - list of trades(current month or previous month)
 * name  - name of the strategy or instrument
 * token - 1 is a token for instruments other numbers for strategies
 * @return returns rating of name param
 * */
class GetRatingUseCase{
    fun execute(list: MutableList<Trade>, name: String, token:Int): Int{
        var wins = 0
        var defeats = 0
        list.forEach {
            if (token == 1){
                if (it.instrument == name){
                    if (it.tradeResult == Results.Victory.name){
                        wins++
                    }else{
                        defeats++
                    }
                }
            }else{
                if (it.strategy == name){
                    if (it.tradeResult == Results.Victory.name){
                        wins++
                    }else{
                        defeats++
                    }
                }
            }

        }
        return if (wins + defeats == 0){
            0
        }else{
            (wins * 100)/(wins + defeats)
        }
    }
}