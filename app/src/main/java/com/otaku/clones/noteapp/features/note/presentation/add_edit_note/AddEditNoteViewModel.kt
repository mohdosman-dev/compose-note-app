package com.otaku.clones.noteapp.features.note.presentation.add_edit_note

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaku.clones.noteapp.features.note.domain.model.InvalidNoteException
import com.otaku.clones.noteapp.features.note.domain.model.Note
import com.otaku.clones.noteapp.features.note.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val TAG = "AddNoteViewModel"

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _noteTitleState = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter title here"
        )
    )
    val noteTitleState: State<NoteTextFieldState> = _noteTitleState

    private val _noteContentState = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter content here"
        )
    )
    val noteContentState: State<NoteTextFieldState> = _noteContentState

    private val _noteColor = mutableStateOf(Note.noteColors.first().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    var note: Note? = null

    init {
        savedStateHandle.get<Int>("note_id")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteByIdUseCase(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteColor.value = note.color
                        _noteTitleState.value = _noteTitleState.value.copy(
                            text = note.title,
                            isHintVisible = false,
                        )
                        _noteContentState.value = _noteContentState.value.copy(
                            text = note.content,
                            isHintVisible = false,
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitleState.value = _noteTitleState.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && noteTitleState.value.text.isBlank(),
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContentState.value = _noteContentState.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && noteContentState.value.text.isBlank(),
                )
            }
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitleState.value = _noteTitleState.value.copy(
                    text = event.text,
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContentState.value = _noteContentState.value.copy(
                    text = event.text,
                )
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.insertNoteUseCase(
                            Note(
                                color = noteColor.value,
                                title = noteTitleState.value.text,
                                content = noteContentState.value.text,
                                timestamp = Date().time,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        Log.e(TAG, "onEvent: Cannot insert note due to: $e")
                        _eventFlow.emit(UiEvent.ShowSnackBar(e.message ?: "Cannot save the note!"))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}