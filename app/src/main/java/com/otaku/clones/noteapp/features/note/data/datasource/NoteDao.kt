package com.otaku.clones.noteapp.features.note.data.datasource

import androidx.room.*
import com.otaku.clones.noteapp.core.Constants.TABLE_NAME
import com.otaku.clones.noteapp.features.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun getNote(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}