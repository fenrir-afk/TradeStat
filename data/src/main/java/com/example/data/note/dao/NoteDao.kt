package com.example.data.note.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.NoteCardDb


@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getAllNotes(): List<NoteCardDb>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(vararg noteCard: NoteCardDb)
    @Update
    fun update(vararg noteCard: NoteCardDb)
    @Delete
    fun delete(vararg noteCard: NoteCardDb)
}
