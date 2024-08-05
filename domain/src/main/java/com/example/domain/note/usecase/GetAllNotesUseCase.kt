package com.example.domain.note.usecase

import com.example.domain.contracts.NoteRepository
import com.example.domain.model.NoteCard

class GetAllNotesUseCase(private val noteRepository: NoteRepository) {
    fun execute(): List<NoteCard> {
        return noteRepository.getAllNotes()
    }
}