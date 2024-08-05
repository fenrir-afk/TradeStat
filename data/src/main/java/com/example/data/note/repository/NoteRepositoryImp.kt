package com.example.data.note.repository

import com.example.data.note.dataSource.NoteDataSource
import com.example.domain.contracts.NoteRepository
import com.example.domain.model.NoteCard
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(private val dataSource: NoteDataSource):NoteRepository {
    override fun getAllNotes(): List<NoteCard> {
        return dataSource.getAllNotes()
    }

    override fun addNote(noteCard: NoteCard) {
       dataSource.addNote(noteCard)
    }

    override fun updateNote(noteCard: NoteCard) {
        dataSource.updateNote(noteCard)
    }

    override fun deleteNote(noteCard: NoteCard) {
       dataSource.deleteNote(noteCard)
    }
}