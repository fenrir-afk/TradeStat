package com.example.tradestat.di

import com.example.domain.trade.TradeRepository
import com.example.domain.trade.usecase.GetDefeatNumberUseCase
import com.example.domain.trade.usecase.GetLongNumberUseCase
import com.example.domain.trade.usecase.GetShortNumberUseCase
import com.example.domain.trade.usecase.GetWinNumberUseCase
import com.example.domain.user.UserRepository
import com.example.domain.user.usecase.LoginUseCase
import com.example.domain.user.usecase.RegistryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase {
        return LoginUseCase(userRepository)
    }
    @Provides
    fun provideRegistryUseCase(userRepository: UserRepository): RegistryUseCase {
        return RegistryUseCase(userRepository)
    }

    //trade use cases

    @Provides
    fun provideGetShortNumberUseCase(tradeRepository: TradeRepository): GetShortNumberUseCase {
        return GetShortNumberUseCase(tradeRepository)
    }

    @Provides
    fun provideGetLongNumberUseCase(tradeRepository: TradeRepository): GetLongNumberUseCase {
        return GetLongNumberUseCase(tradeRepository)
    }

    @Provides
    fun provideGetWinNumberUseCase(tradeRepository: TradeRepository): GetWinNumberUseCase {
        return GetWinNumberUseCase(tradeRepository)
    }

    @Provides
    fun provideGetDefeatNumberUseCase(tradeRepository: TradeRepository): GetDefeatNumberUseCase {
        return GetDefeatNumberUseCase(tradeRepository)
    }
}