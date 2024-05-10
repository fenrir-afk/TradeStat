package com.example.tradestat.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param id the id of the card
 * @param noteTexts list of all texts of the card
 * @param noteImages list of all  images(urls) of the card
 * @param title title of the card
 * */
@Entity(tableName = "note_table")
data class NoteCard(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "note_texts") val noteTexts: MutableList<String>,
    @ColumnInfo(name = "note_images") val noteImages: MutableList<String>,
    @ColumnInfo(name = "title") val title:String
)