package com.example.data.note.dataSource

import com.example.data.mapper.toDbData
import com.example.data.mapper.toDomain
import com.example.data.note.dao.NoteDao
import com.example.domain.model.NoteCard
import javax.inject.Inject

class NoteDataSourceImp @Inject constructor(private val dao: NoteDao):NoteDataSource {
    override fun getAllNotes(): List<NoteCard> {
        return dao.getAllNotes().map { it.toDomain() }
    }

    override fun addNote(noteCard: NoteCard) {
       dao.insertNote(noteCard.toDbData())
    }

    override fun updateNote(noteCard: NoteCard) {
        dao.update(noteCard.toDbData())
    }

    override fun deleteNote(noteCard: NoteCard) {
       dao.delete(noteCard.toDbData())
    }
}