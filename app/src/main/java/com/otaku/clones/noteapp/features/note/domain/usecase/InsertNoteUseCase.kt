package com.otaku.clones.noteapp.features.note.domain.usecase

import com.otaku.clones.noteapp.features.note.data.repository.NoteRepository
import com.otaku.clones.noteapp.features.note.domain.model.InvalidNoteException
import com.otaku.clones.noteapp.features.note.domain.model.Note

class InsertNoteUseCase(
    private val repository: NoteRepository,
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty")
        }
        repository.insertNote(note)
    }
}