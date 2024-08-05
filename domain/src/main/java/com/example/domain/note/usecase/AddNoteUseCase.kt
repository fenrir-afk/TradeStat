package com.example.domain.note.usecase

import com.example.domain.contracts.NoteRepository
import com.example.domain.model.NoteCard

class AddNoteUseCase(private val noteRepository: NoteRepository) {
    fun execute(noteCard: NoteCard){
        noteRepository.addNote(noteCard)
    }
}