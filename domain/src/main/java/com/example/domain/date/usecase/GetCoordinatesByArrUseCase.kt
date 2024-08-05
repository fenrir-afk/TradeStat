package com.example.domain.date.usecase


import com.example.domain.model.ChartEntry
import com.example.domain.model.Results
import com.example.domain.model.Trade

class GetCoordinatesByArrUseCase{
    fun execute(tradeArr: List<Trade>): MutableList<ChartEntry> {
        val list:MutableList<ChartEntry> = mutableListOf()
        list.add(ChartEntry(0f,0f))
        var verticalCounter = 0f
        var horizontalCounter: Float
        repeat(tradeArr.size) {
            horizontalCounter = it.toFloat() + 1f
            if (tradeArr[it].tradeResult == Results.Victory.name){
                verticalCounter++
            }else{
                verticalCounter--
            }
            list.add(ChartEntry(horizontalCounter,verticalCounter))
        }
        return list
    }
}