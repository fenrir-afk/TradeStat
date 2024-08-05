package com.example.domain.note.usecase

import com.example.domain.contracts.NoteRepository
import com.example.domain.model.NoteCard

class DeleteNoteUseCase(private val noteRepository: NoteRepository) {
    fun execute(noteCard: NoteCard){
        noteRepository.deleteNote(noteCard)
    }
}