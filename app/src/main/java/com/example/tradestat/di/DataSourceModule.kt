package com.example.tradestat.di

import com.example.data.main.home.dataSource.HomeDataSource
import com.example.data.main.home.dataSource.HomeDataSourceImp
import com.example.data.trade.dao.TradesDao
import com.example.data.user.dataSource.UserDataSource
import com.example.data.user.dataSource.UserDataSourceImp
import com.example.data.user.dao.UserDao
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
    fun provideTradeDataSource(tradeDao: TradesDao): HomeDataSource {
        return HomeDataSourceImp(tradeDao)
    }
}