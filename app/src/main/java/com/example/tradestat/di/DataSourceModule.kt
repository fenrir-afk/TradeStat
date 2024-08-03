package com.example.tradestat.di

import com.example.data.instrument.dao.InstrumentDao
import com.example.data.instrument.dataSource.InstrumentDataSource
import com.example.data.instrument.dataSource.InstrumentDataSourceImp
import com.example.data.news.api.QuotesApi
import com.example.data.news.dataSource.NewsDataSource
import com.example.data.news.dataSource.NewsDataSourceImp
import com.example.data.note.dao.NoteDao
import com.example.data.note.dataSource.NoteDataSource
import com.example.data.note.dataSource.NoteDataSourceImp
import com.example.data.strategy.dao.StrategiesDao
import com.example.data.strategy.dataSource.StrategyDataSource
import com.example.data.strategy.dataSource.StrategyDataSourceImp
import com.example.data.trade.dao.TradesDao
import com.example.data.trade.dataSource.TradeDataSource
import com.example.data.trade.dataSource.TradeDataSourceImp
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
    fun provideUserDataSource(userDao: UserDao): UserDataSource {
        return UserDataSourceImp(userDao)
    }
    @Provides
    @Singleton
    fun provideTradeDataSource(tradeDao: TradesDao): TradeDataSource {
        return TradeDataSourceImp(tradeDao)
    }
    @Provides
    @Singleton
    fun provideStrategyDataSource(strategyDao: StrategiesDao): StrategyDataSource {
        return StrategyDataSourceImp(strategyDao)
    }
    @Provides
    @Singleton
    fun provideInstrumentDataSource(instrumentDao: InstrumentDao): InstrumentDataSource {
        return InstrumentDataSourceImp(instrumentDao)
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(noteDao: NoteDao): NoteDataSource {
        return NoteDataSourceImp(noteDao)
    }

    @Provides
    @Singleton
    fun provideNewsDataSource(api: QuotesApi): NewsDataSource {
        return NewsDataSourceImp(api)
    }
}