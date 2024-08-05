package com.example.tradestat.di.usecase

import com.example.domain.contracts.StrategyRepository
import com.example.domain.main.trade.usecase.AddStrategyUseCase
import com.example.domain.main.trade.usecase.DeleteStrategyUseCase
import com.example.domain.main.trade.usecase.GetAllStrategiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class StrategyModule {
    @Provides
    fun provideAddStrategyUseCase(rep: StrategyRepository): AddStrategyUseCase {
        return AddStrategyUseCase(rep)
    }
    @Provides
    fun provideDeleteStrategyUseCase(rep: StrategyRepository): DeleteStrategyUseCase {
        return DeleteStrategyUseCase(rep)
    }
    @Provides
    fun provideGetAllStrategiesUseCase(rep: StrategyRepository): GetAllStrategiesUseCase {
        return GetAllStrategiesUseCase(rep)
    }

}