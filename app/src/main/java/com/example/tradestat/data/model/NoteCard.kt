package com.example.tradestat.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteCard(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "note_texts") val noteTexts: MutableList<String>,
    @ColumnInfo(name = "note_images") val noteImages: MutableList<String>,
    @ColumnInfo(name = "title") val title:String
)