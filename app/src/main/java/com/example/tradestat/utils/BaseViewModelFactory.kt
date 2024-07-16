package com.example.tradestat.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.ui.analysis.AnalysisViewModel
import com.example.tradestat.ui.dateStatistic.DateViewModel
import com.example.tradestat.ui.home.HomeViewModel
import com.example.tradestat.ui.instrumentStatistic.InstrumentViewModel
import com.example.tradestat.ui.login.LoginViewModel
import com.example.tradestat.ui.news.NewsViewModel
import com.example.tradestat.ui.notes.NoteViewModel
import com.example.tradestat.ui.registry.RegistryViewModel
import com.example.tradestat.ui.results.ResultsViewModel
import com.example.tradestat.ui.strategyStatistic.StrategyViewModel
import com.example.tradestat.ui.trade.TradeViewModel

/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
val BaseViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            val tradesRepository = TodoApplication().tradesRepository
            val application = TodoApplication().application
            when {
                isAssignableFrom(TradeViewModel::class.java) ->
                    TradeViewModel(application,tradesRepository)
                isAssignableFrom(StrategyViewModel::class.java) ->
                    StrategyViewModel(application,tradesRepository)
                isAssignableFrom(ResultsViewModel::class.java) ->
                    ResultsViewModel(application,tradesRepository)
                isAssignableFrom(RegistryViewModel::class.java) ->
                    RegistryViewModel(tradesRepository)
                isAssignableFrom(NoteViewModel::class.java) ->
                    NoteViewModel(application,tradesRepository)
                isAssignableFrom(NewsViewModel::class.java) ->
                    NewsViewModel(tradesRepository)
                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(tradesRepository)
                isAssignableFrom(DateViewModel::class.java) ->
                    DateViewModel(application,tradesRepository)
                isAssignableFrom(InstrumentViewModel::class.java) ->
                    InstrumentViewModel(application,tradesRepository)
                isAssignableFrom(AnalysisViewModel::class.java) ->
                    AnalysisViewModel(application,tradesRepository)
                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(application,tradesRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}