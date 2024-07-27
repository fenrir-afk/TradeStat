package com.example.tradestat.di

import com.example.data.login.dataSource.LoginDataSource
import com.example.data.login.dataSource.LoginDataSourceImp
import com.example.data.login.locale.dao.UserDao
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
    fun provideLoginDataSource(userDao: UserDao): LoginDataSource {
        return LoginDataSourceImp(userDao)
    }
}