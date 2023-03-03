package com.otaku.clones.noteapp.features.note.domain.repository

import com.otaku.clones.noteapp.features.note.data.datasource.NoteDao
import com.otaku.clones.noteapp.features.note.data.repository.NoteRepository
import com.otaku.clones.noteapp.features.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNote(id)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}