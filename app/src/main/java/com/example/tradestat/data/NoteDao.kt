package com.example.tradestat.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverter
import androidx.room.Update
import com.example.tradestat.data.model.NoteCard

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table")
    fun getAllNotes(): List<NoteCard>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(vararg noteCard: NoteCard)
    @Update
    fun update(vararg noteCard: NoteCard)
}
