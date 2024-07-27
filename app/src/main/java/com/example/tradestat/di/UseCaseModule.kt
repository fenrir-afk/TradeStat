package com.example.tradestat.di

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
}