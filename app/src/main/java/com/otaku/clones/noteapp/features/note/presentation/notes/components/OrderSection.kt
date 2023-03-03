package com.otaku.clones.noteapp.features.note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.otaku.clones.noteapp.features.note.domain.utils.NoteOrder
import com.otaku.clones.noteapp.features.note.domain.utils.OrderType
import com.otaku.clones.noteapp.ui.theme.NoteAppTheme

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChanged: (newOrder: NoteOrder) -> Unit,
) {
    var list = listOf<String>("1", "2", "3", "4")
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                title = "Title",
                selected = noteOrder is NoteOrder.Title,
                onSelect = {
                    onOrderChanged(NoteOrder.Title(noteOrder.type))
                })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                title = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelect = {
                    onOrderChanged(NoteOrder.Date(noteOrder.type))
                })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                title = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelect = {
                    onOrderChanged(NoteOrder.Color(noteOrder.type))
                })
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                title = "Ascending",
                selected = noteOrder.type is OrderType.Ascending,
                onSelect = {
                    onOrderChanged(noteOrder.copy(OrderType.Ascending))
                })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                title = "Descending",
                selected = noteOrder.type is OrderType.Descending,
                onSelect = {
                    onOrderChanged(noteOrder.copy(OrderType.Descending))
                })
        }
    }
}


// Components
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteAppTheme {
        OrderSection(
            noteOrder = NoteOrder.Date(OrderType.Descending),

            ) {
        }
    }
}
