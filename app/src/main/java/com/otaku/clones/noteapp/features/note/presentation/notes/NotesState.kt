package com.otaku.clones.noteapp.features.note.presentation.notes

import com.otaku.clones.noteapp.features.note.domain.model.Note
import com.otaku.clones.noteapp.features.note.domain.utils.NoteOrder
import com.otaku.clones.noteapp.features.note.domain.utils.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
