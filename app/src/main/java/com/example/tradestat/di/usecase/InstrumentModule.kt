package com.example.tradestat.di.usecase

import com.example.domain.contracts.InstrumentRepository
import com.example.domain.main.trade.usecase.AddInstrumentUseCase
import com.example.domain.main.trade.usecase.DeleteInstrumentUseCase
import com.example.domain.main.trade.usecase.GetAllInstrumentsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class InstrumentModule {
    @Provides
    fun provideGetAllInstrumentsUseCase(rep: InstrumentRepository): GetAllInstrumentsUseCase {
        return GetAllInstrumentsUseCase(rep)
    }

    @Provides
    fun provideAddInstrumentUseCase(rep: InstrumentRepository): AddInstrumentUseCase {
        return AddInstrumentUseCase(rep)
    }

    @Provides
    fun provideDeleteInstrumentUseCase(rep: InstrumentRepository): DeleteInstrumentUseCase {
        return DeleteInstrumentUseCase(rep)
    }
}