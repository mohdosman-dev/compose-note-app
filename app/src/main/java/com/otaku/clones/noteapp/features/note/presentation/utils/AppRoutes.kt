package com.otaku.clones.noteapp.features.note.presentation.utils

sealed class AppRoutes(val route: String) {
    object NotesApp : AppRoutes("notes_screen")
    object AddEditNoteApp : AppRoutes("add_edit_note_screen")
}
