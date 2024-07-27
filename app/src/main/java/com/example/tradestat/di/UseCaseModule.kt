package com.example.tradestat.di

import com.example.domain.login.LoginRepository
import com.example.domain.login.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }
}