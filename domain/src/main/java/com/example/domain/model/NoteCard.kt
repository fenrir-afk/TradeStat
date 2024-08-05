package com.example.domain.model


data class NoteCard(
    val id: Int,

    val noteTexts: List<String>,

    val noteImages: List<String>,

    val title: String
)