package com.otaku.clones.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.otaku.clones.noteapp.features.note.presentation.add_edit_note.AddEditNoteScreen
import com.otaku.clones.noteapp.features.note.presentation.notes.NoteScreen
import com.otaku.clones.noteapp.features.note.presentation.utils.AppRoutes
import com.otaku.clones.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AppRoutes.NotesApp.route
                    ) {
                        composable(
                            route = AppRoutes.NotesApp.route,
                        ) {
                            NoteScreen(navController = navController)
                        }
                        composable(
                            route = AppRoutes.AddEditNoteApp.route
                                    + "?note_id={note_id}&note_color={note_color}",
                            arguments = listOf(
                                navArgument(
                                    name = "note_id"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "note_coloe"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            ),
                        ) {
                            val color = it.arguments?.getInt("note_color") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}

// Components
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteAppTheme {

    }
}