package com.example.tradestat.di.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.main.trade.usecase.AddInstrumentUseCase
import com.example.domain.main.trade.usecase.AddStrategyUseCase
import com.example.domain.main.trade.usecase.AddTradeUseCase
import com.example.domain.main.trade.usecase.DeleteTradeUseCase
import com.example.domain.main.trade.usecase.GetAllInstrumentsUseCase
import com.example.domain.main.trade.usecase.GetAscendingTradesUseCase
import com.example.domain.main.trade.usecase.GetDescendingTradesUseCase
import com.example.domain.main.trade.usecase.SortByInstrumentUseCase
import com.example.domain.main.trade.usecase.SortByStrategyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TradeUseCaseModule {
    @Provides
    fun provideAddTradeUseCase(rep: TradeRepository): AddTradeUseCase {
        return AddTradeUseCase(rep)
    }
    @Provides
    fun provideDeleteTradeUseCase(rep: TradeRepository): DeleteTradeUseCase {
        return DeleteTradeUseCase(rep)
    }
    @Provides
    fun provideListTradesSortedByDescending(rep: TradeRepository): GetDescendingTradesUseCase {
        return GetDescendingTradesUseCase(rep)
    }
    @Provides
    fun provideListTradesSortedByAscending(rep: TradeRepository): GetAscendingTradesUseCase {
        return GetAscendingTradesUseCase(rep)
    }
    @Provides
    fun provideListTradesSortedByStrategy(rep: TradeRepository): SortByStrategyUseCase {
        return SortByStrategyUseCase(rep)
    }
    @Provides
    fun provideListTradesSortedByInstrument(rep: TradeRepository): SortByInstrumentUseCase {
        return SortByInstrumentUseCase(rep)
    }
    @Provides
    fun provideListInstruments(rep: TradeRepository): GetAllInstrumentsUseCase {
        return GetAllInstrumentsUseCase(rep)
    }
    @Provides
    fun provideAddStrategyUseCase(rep: TradeRepository): AddStrategyUseCase {
        return AddStrategyUseCase(rep)
    }

    @Provides
    fun provideAddInstrumentUseCase(rep: TradeRepository): AddInstrumentUseCase {
        return AddInstrumentUseCase(rep)
    }
}