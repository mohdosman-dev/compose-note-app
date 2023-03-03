package com.otaku.clones.noteapp.features.note.domain.usecase

import com.otaku.clones.noteapp.features.note.data.repository.NoteRepository
import com.otaku.clones.noteapp.features.note.domain.model.Note

class GetNoteByIdUseCase(
    private val repository: NoteRepository,
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}