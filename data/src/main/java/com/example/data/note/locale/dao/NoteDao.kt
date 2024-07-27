package com.example.data.note.locale.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.note.locale.entity.NoteCard


@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getAllNotes(): List<NoteCard>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(vararg noteCard: NoteCard)
    @Update
    fun update(vararg noteCard: NoteCard)
    @Delete
    fun delete(vararg noteCard: NoteCard)
}
