package com.example.tradestat.di.usecase

import com.example.domain.contracts.InstrumentRepository
import com.example.domain.contracts.StrategyRepository
import com.example.domain.contracts.TradeRepository
import com.example.domain.date.usecase.GetCoordinatesByArrUseCase
import com.example.domain.instrument.usecase.GetInstrumentsRatingUseCase
import com.example.domain.strategy.usecase.GetStrategiesChartUseCase
import com.example.domain.strategy.usecase.GetStrategiesRatingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RelatedUseCaseModule {
    @Provides
    fun provideGetStrategiesChartUseCase(rep1: TradeRepository,rep2: StrategyRepository): GetStrategiesChartUseCase {
        return GetStrategiesChartUseCase(rep1,rep2)
    }
    @Provides
    fun provideGetStrategiesRatingUseCase(rep1: TradeRepository,rep2: StrategyRepository): GetStrategiesRatingUseCase {
        return GetStrategiesRatingUseCase(rep1,rep2)
    }


    @Provides
    fun provideGetInstrumentsRatingUseCase(rep1: TradeRepository,rep2: InstrumentRepository): GetInstrumentsRatingUseCase {
        return GetInstrumentsRatingUseCase(rep1,rep2)
    }

    @Provides
    fun provideGetCoordinatesByArrUseCase(): GetCoordinatesByArrUseCase {
        return GetCoordinatesByArrUseCase()
    }
}