package com.example.tradestat.di.usecase

import com.example.domain.contracts.TradeRepository
import com.example.domain.main.home.usecase.GetDefeatNumberUseCase
import com.example.domain.main.home.usecase.GetLongNumberUseCase
import com.example.domain.main.home.usecase.GetShortNumberUseCase
import com.example.domain.main.home.usecase.GetWinNumberUseCase
import com.example.domain.main.trade.usecase.AddTradeUseCase
import com.example.domain.main.trade.usecase.DeleteTradeUseCase
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


    //home useCases

    @Provides
    fun provideGetDefeatNumberUseCase(rep: TradeRepository): GetDefeatNumberUseCase {
        return GetDefeatNumberUseCase(rep)
    }

    @Provides
    fun provideGetLongNumberUseCase(rep: TradeRepository): GetLongNumberUseCase {
        return GetLongNumberUseCase(rep)
    }

    @Provides
    fun provideGetShortNumberUseCase(rep: TradeRepository): GetShortNumberUseCase {
        return GetShortNumberUseCase(rep)
    }
    @Provides
    fun provideGetWinNumberUseCase(rep: TradeRepository): GetWinNumberUseCase {
        return GetWinNumberUseCase(rep)
    }



}