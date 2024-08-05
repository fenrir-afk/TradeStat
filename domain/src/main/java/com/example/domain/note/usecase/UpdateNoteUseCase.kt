package com.example.domain.note.usecase

import com.example.domain.contracts.NoteRepository
import com.example.domain.model.NoteCard

class UpdateNoteUseCase(private val noteRepository: NoteRepository) {
    fun execute(noteCard: NoteCard){
        noteRepository.updateNote(noteCard)
    }
}