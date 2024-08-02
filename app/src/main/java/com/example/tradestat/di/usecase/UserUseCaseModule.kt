package com.example.tradestat.di.usecase

import com.example.domain.contracts.UserRepository
import com.example.domain.user.usecase.LoginUseCase
import com.example.domain.user.usecase.RegistryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserUseCaseModule {

    @Provides
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase {
        return LoginUseCase(userRepository)
    }
    @Provides
    fun provideRegistryUseCase(userRepository: UserRepository): RegistryUseCase {
        return RegistryUseCase(userRepository)
    }
}