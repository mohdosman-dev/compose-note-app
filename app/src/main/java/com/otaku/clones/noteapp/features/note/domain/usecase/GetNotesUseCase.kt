package com.otaku.clones.noteapp.features.note.domain.usecase

import com.otaku.clones.noteapp.features.note.data.repository.NoteRepository
import com.otaku.clones.noteapp.features.note.domain.model.Note
import com.otaku.clones.noteapp.features.note.domain.utils.NoteOrder
import com.otaku.clones.noteapp.features.note.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class GetNotesUseCase(
    private val repository: NoteRepository,
) {

    operator fun invoke(order: NoteOrder = NoteOrder.Date(OrderType.Descending)): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (order.type) {
                is OrderType.Ascending -> {
                    when (order) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase(Locale.ROOT) }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when (order) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase(Locale.ROOT) }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}