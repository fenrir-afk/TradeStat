package com.example.tradestat.di

import com.example.data.trade.dataSource.TradeDataSource
import com.example.data.trade.dataSource.TradeDataSourceImp
import com.example.data.trade.locale.dao.TradesDao
import com.example.data.trade.locale.entity.Trade
import com.example.data.user.dataSource.UserDataSource
import com.example.data.user.dataSource.UserDataSourceImp
import com.example.data.user.locale.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideLoginDataSource(userDao: UserDao): UserDataSource {
        return UserDataSourceImp(userDao)
    }
    @Provides
    @Singleton
    fun provideTradeDataSource(tradeDao: TradesDao): TradeDataSource {
        return TradeDataSourceImp(tradeDao)
    }
}