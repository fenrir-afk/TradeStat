package com.example.domain.main.home.usecase

import com.example.domain.model.Directions

class GetLongNumberUseCase(val rep: HomeRepository) {
    fun execute(): Int{
        return rep.getNumberByDirection(Directions.Long.toString())
    }
}