package com.example.domain.trade.usecase

import com.example.domain.trade.TradeRepository
import com.example.domain.util.Results

class GetDefeatNumberUseCase(val rep: TradeRepository) {
    fun execute(): Int{
        return rep.getNumberByResult(Results.Defeat.toString())
    }
}