package com.example.tradestat.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.TradeDatabase
import com.example.data.instrument.locale.dao.InstrumentDao
import com.example.data.user.locale.dao.UserDao
import com.example.data.note.locale.dao.NoteDao
import com.example.data.strategy.locale.dao.StrategiesDao
import com.example.data.trade.locale.dao.TradesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context,
    ): TradeDatabase {
        return Room
            .databaseBuilder(
            context.applicationContext,
            TradeDatabase::class.java,
            "trade_database"
        ).build()
    }
    @Provides
    fun provideInstrumentDao(tradeDatabase: TradeDatabase): InstrumentDao {
        return tradeDatabase.instrumentDao()
    }
    @Provides
    fun provideStrategyDao(tradeDatabase: TradeDatabase): StrategiesDao {
        return tradeDatabase.strategiesDao()
    }
    @Provides
    fun provideNoteDao(tradeDatabase: TradeDatabase): NoteDao {
        return tradeDatabase.noteDao()
    }
    @Provides
    fun provideTradeDao(tradeDatabase: TradeDatabase): TradesDao {
        return tradeDatabase.tradesDao()
    }
    @Provides
    fun provideUserDao(tradeDatabase: TradeDatabase): UserDao {
        return tradeDatabase.userDao()
    }
}