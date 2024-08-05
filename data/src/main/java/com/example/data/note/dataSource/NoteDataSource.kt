package com.example.data.note.dataSource

import com.example.domain.model.NoteCard

interface NoteDataSource {
    fun getAllNotes(): List<NoteCard>
    fun addNote(noteCard: NoteCard)
    fun updateNote(noteCard: NoteCard)
    fun deleteNote(noteCard: NoteCard)
}