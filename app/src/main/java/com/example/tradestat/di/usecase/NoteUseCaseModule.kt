package com.example.tradestat.di.usecase

import com.example.domain.contracts.NoteRepository
import com.example.domain.note.usecase.AddNoteUseCase
import com.example.domain.note.usecase.DeleteNoteUseCase
import com.example.domain.note.usecase.GetAllNotesUseCase
import com.example.domain.note.usecase.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NoteUseCaseModule {
    @Provides
    fun provideAddNoteUseCase(rep1: NoteRepository): AddNoteUseCase {
        return AddNoteUseCase(rep1)
    }
    @Provides
    fun provideDeleteNoteUseCase(rep1: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(rep1)
    }
    @Provides
    fun provideUpdateNoteUseCase(rep1: NoteRepository): UpdateNoteUseCase {
        return UpdateNoteUseCase(rep1)
    }
    @Provides
    fun provideGetAllNotesUseCase(rep1: NoteRepository): GetAllNotesUseCase {
        return GetAllNotesUseCase(rep1)
    }
}