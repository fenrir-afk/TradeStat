package com.example.domain.contracts

import com.example.domain.model.NoteCard

interface NoteRepository {
    fun getAllNotes(): List<NoteCard>

    fun addNote(noteCard: NoteCard)
    fun updateNote(noteCard: NoteCard)
    fun deleteNote(noteCard: NoteCard)
}