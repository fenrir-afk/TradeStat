package com.example.tradestat.di

import com.example.data.instrument.repository.InstrumentRepositoryImp
import com.example.data.news.repository.NewsRepositoryImp
import com.example.data.note.repository.NoteRepositoryImp
import com.example.data.strategy.repository.StrategyRepositoryImp
import com.example.data.trade.repository.TradeRepositoryImp
import com.example.data.user.repository.UserRepositoryImp
import com.example.domain.contracts.InstrumentRepository
import com.example.domain.contracts.NewsRepository
import com.example.domain.contracts.NoteRepository
import com.example.domain.contracts.StrategyRepository
import com.example.domain.contracts.TradeRepository
import com.example.domain.contracts.UserRepository
import com.example.domain.date.usecase.GetCoordinatesByArrUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideUserRepository(userRepositoryImp: UserRepositoryImp): UserRepository

    @Binds
    abstract fun provideTradeRepository(tradeRepositoryImp: TradeRepositoryImp): TradeRepository

    @Binds
    abstract fun provideStrategyRepository(strategyRepositoryImp: StrategyRepositoryImp): StrategyRepository

    @Binds
    abstract fun provideInstrumentRepository(instrumentRepositoryImp: InstrumentRepositoryImp): InstrumentRepository

    @Binds
    abstract fun provideNoteRepository(noteRepositoryImp: NoteRepositoryImp): NoteRepository

    @Binds
    abstract fun provideNewsRepository(newsRepositoryImp: NewsRepositoryImp): NewsRepository

}