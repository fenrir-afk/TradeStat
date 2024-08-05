package com.example.domain.results.usecase

import com.example.domain.model.Trade
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * In this method we are getting array of previous month or current month based on token
 * token 1 mean current month other numbers mean previous month
 * @return return all trades added in the month
 * trades -  all trades of app
 * */
class GetMonthTradesUseCase{
    fun execute(trades: List<Trade>, token: Int): MutableList<Trade>{
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val lastMonth = if (currentMonth == 0) 11 else currentMonth - 1
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val dealsThisMonth = trades.filter { trade ->
            val tradeDate = dateFormat.parse(trade.ADDate)
            val dealMonth = tradeDate?.month ?: -1
            val dealYear = tradeDate?.year?.plus(1900)
            if (token == 1){
                dealMonth == currentMonth && dealYear == currentYear
            }else{
                dealMonth == lastMonth && dealYear == currentYear
            }
        }
        return dealsThisMonth as MutableList<Trade>
    }
}