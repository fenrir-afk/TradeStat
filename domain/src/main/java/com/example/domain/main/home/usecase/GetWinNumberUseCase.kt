package com.example.domain.main.home.usecase

import com.example.domain.model.Results

class GetWinNumberUseCase(val rep: HomeRepository) {
    fun execute(): Int{
        return rep.getNumberByResult(Results.Victory.toString())
    }
}