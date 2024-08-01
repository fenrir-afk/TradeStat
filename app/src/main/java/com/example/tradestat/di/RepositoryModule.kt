package com.example.tradestat.di


import com.example.data.trade.dataSource.TradeDataSource
import com.example.data.trade.dataSource.TradeDataSourceImp
import com.example.data.trade.repository.TradeRepositoryImp
import com.example.data.user.dataSource.UserDataSource
import com.example.data.user.repository.UserRepositoryImp
import com.example.domain.trade.TradeRepository
import com.example.domain.user.UserRepository
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
        tradeDataSource: TradeDataSource
    ): TradeRepository {
        return TradeRepositoryImp(tradeDataSource)
    }
}