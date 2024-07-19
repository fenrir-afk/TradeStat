package com.example.tradestat.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.repository.TradesRepository
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
@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory(
    private val tradesRepository: TradesRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            TradeViewModel::class.java -> TradeViewModel(tradesRepository)
            StrategyViewModel::class.java -> StrategyViewModel(tradesRepository)
            ResultsViewModel::class.java -> ResultsViewModel(tradesRepository)
            RegistryViewModel::class.java -> RegistryViewModel(tradesRepository)
            NoteViewModel::class.java -> NoteViewModel(tradesRepository)
            NewsViewModel::class.java -> NewsViewModel(tradesRepository)
            LoginViewModel::class.java -> LoginViewModel(tradesRepository)
            DateViewModel::class.java -> DateViewModel(tradesRepository)
            InstrumentViewModel::class.java -> InstrumentViewModel(tradesRepository)
            AnalysisViewModel::class.java -> AnalysisViewModel(tradesRepository)
            HomeViewModel::class.java -> HomeViewModel(tradesRepository)
            // ... other ViewModel classes
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
    }
}