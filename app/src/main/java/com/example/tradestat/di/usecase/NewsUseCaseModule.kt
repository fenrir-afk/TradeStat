package com.example.tradestat.di.usecase

import com.example.domain.contracts.NewsRepository
import com.example.domain.contracts.StrategyRepository
import com.example.domain.main.news.usecase.GetForexDataUseCase
import com.example.domain.main.trade.usecase.AddStrategyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NewsUseCaseModule {
    @Provides
    fun provideGetForexDataUseCase(rep: NewsRepository): GetForexDataUseCase {
        return GetForexDataUseCase(rep)
    }
}