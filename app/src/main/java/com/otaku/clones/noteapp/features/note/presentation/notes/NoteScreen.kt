package com.otaku.clones.noteapp.features.note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.otaku.clones.noteapp.features.note.presentation.notes.components.NoteCard
import com.otaku.clones.noteapp.features.note.presentation.notes.components.OrderSection
import com.otaku.clones.noteapp.features.note.presentation.utils.AppRoutes
//import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        route = AppRoutes.AddEditNoteApp.route,
                    )
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note"
                )
            }

        },
        scaffoldState = scaffoldState,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your notes",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                )
                IconButton(onClick = {
                    viewModel.onEvent(NotesEvent.ToggleOrderSection)
                }) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = null
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                OrderSection(
                    noteOrder = state.noteOrder,
                    onOrderChanged = { newOrder ->
                        viewModel.onEvent(NotesEvent.Order(newOrder))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) { note ->
                    NoteCard(note = note,
                        onDeleteNote = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "You've delete note successfully",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Long,
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable() {}
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
