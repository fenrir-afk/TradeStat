package com.example.domain.main.home.usecase

import com.example.domain.model.Results

class GetDefeatNumberUseCase(val rep: HomeRepository) {
    fun execute(): Int{
        return rep.getNumberByResult(Results.Defeat.toString())
    }
}