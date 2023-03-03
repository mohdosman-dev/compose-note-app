package com.otaku.clones.noteapp.features.note.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.otaku.clones.noteapp.features.note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false,
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DB_NAME = "note_db"
    }
}