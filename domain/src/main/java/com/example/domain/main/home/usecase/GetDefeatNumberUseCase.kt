package com.example.domain.main.home.usecase

import com.example.domain.contracts.TradeRepository

class GetDefeatNumberUseCase(val rep: TradeRepository) {
    fun execute(): Int{
        return rep.getDefNumber()
    }
}