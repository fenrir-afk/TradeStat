package com.example.data.mapper


import com.example.data.entity.NoteCardDb
import com.example.domain.model.NoteCard

fun NoteCard.toDbData() = NoteCardDb(
    id = id,
    noteTexts =noteTexts,
    noteImages = noteImages,
    title = title
)
fun NoteCardDb.toDomain() = NoteCard(
    id = id,
    noteTexts =noteTexts,
    noteImages = noteImages,
    title = title
)
