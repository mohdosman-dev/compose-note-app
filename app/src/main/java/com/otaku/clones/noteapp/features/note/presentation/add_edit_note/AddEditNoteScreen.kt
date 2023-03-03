package com.otaku.clones.noteapp.features.note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.otaku.clones.noteapp.features.note.domain.model.Note
import com.otaku.clones.noteapp.features.note.presentation.add_edit_note.Components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    modifier: Modifier = Modifier,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    navController: NavController,
    noteColor: Int,
) {
    val titleState = viewModel.noteTitleState.value
    val contentState = viewModel.noteContentState.value
    val notedBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackBar -> {
                    // TODO: Show the snack bar here
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short,
                        actionLabel = "Retry",
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(AddEditNoteEvent.SaveNote)
                    }
                }
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    // TODO: Save the note here
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save note"
                )
            }
        },
        scaffoldState = scaffoldState,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(notedBackgroundAnimatable.value)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(Note.noteColors) { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp, 50.dp)
//                            .shadow(50.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt)
                                    Color.Black else Color.Transparent,
                                shape = CircleShape,
                            )
                            .clickable {
                                scope.launch {
                                    notedBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
                item { Spacer(modifier = Modifier.width(8.dp)) }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChanged = { newValue ->
                    viewModel.onEvent(
                        AddEditNoteEvent.EnteredTitle(
                            newValue
                        )
                    )
                },
                onFocusChange = { focusState ->
                    viewModel.onEvent(
                        AddEditNoteEvent.ChangeTitleFocus(
                            focusState
                        )
                    )
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChanged = { newValue ->
                    viewModel.onEvent(
                        AddEditNoteEvent.EnteredContent(
                            newValue
                        )
                    )
                },
                onFocusChange = { focusState ->
                    viewModel.onEvent(
                        AddEditNoteEvent.ChangeContentFocus(
                            focusState
                        )
                    )
                },
                textStyle = MaterialTheme.typography.body1,
                singleLine = false,
            modifier = Modifier.fillMaxHeight(),
            )
        }
    }
}
//
//@Preview(name = "AddEditNoteScreen")
//@Composable
//private fun PreviewAddEditNoteScreen() {
//    AddEditNoteScreen()
//}