package com.example.tradestat.di


import com.example.data.main.home.dataSource.HomeDataSource
import com.example.data.main.home.repository.HomeRepositoryImp
import com.example.data.user.dataSource.UserDataSource
import com.example.data.user.repository.UserRepositoryImp
import com.example.domain.contracts.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
       loginDataSource: UserDataSource
    ): UserRepository {
        return UserRepositoryImp(loginDataSource)
    }
    @Provides
    @Singleton
    fun provideTradeRepository(
        tradeDataSource: HomeDataSource
    ): HomeRepository {
        return HomeRepositoryImp(tradeDataSource)
    }
}