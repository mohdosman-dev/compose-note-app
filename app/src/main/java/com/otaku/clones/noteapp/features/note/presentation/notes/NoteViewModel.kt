package com.otaku.clones.noteapp.features.note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaku.clones.noteapp.features.note.domain.model.Note
import com.otaku.clones.noteapp.features.note.domain.usecase.NoteUseCases
import com.otaku.clones.noteapp.features.note.domain.utils.NoteOrder
import com.otaku.clones.noteapp.features.note.domain.utils.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (event.noteOrder::class == _state.value.noteOrder::class
                    && event.noteOrder.type == _state.value.noteOrder.type
                ) {
                    return
                }

                getNotes(event.noteOrder)
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.insertNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()

        getNotesJob = noteUseCases.getNotesUseCase(noteOrder)
            .onEach { notes ->
                _state.value = _state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }
}