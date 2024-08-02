package com.example.tradestat.di.usecase

import com.example.domain.main.home.usecase.GetDefeatNumberUseCase
import com.example.domain.main.home.usecase.GetLongNumberUseCase
import com.example.domain.main.home.usecase.GetShortNumberUseCase
import com.example.domain.main.home.usecase.GetWinNumberUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HomeUseCaseModule {

    @Provides
    fun provideGetShortNumberUseCase(tradeRepository: HomeRepository): GetShortNumberUseCase {
        return GetShortNumberUseCase(tradeRepository)
    }

    @Provides
    fun provideGetLongNumberUseCase(tradeRepository: HomeRepository): GetLongNumberUseCase {
        return GetLongNumberUseCase(tradeRepository)
    }

    @Provides
    fun provideGetWinNumberUseCase(tradeRepository: HomeRepository): GetWinNumberUseCase {
        return GetWinNumberUseCase(tradeRepository)
    }

    @Provides
    fun provideGetDefeatNumberUseCase(tradeRepository: HomeRepository): GetDefeatNumberUseCase {
        return GetDefeatNumberUseCase(tradeRepository)
    }
}