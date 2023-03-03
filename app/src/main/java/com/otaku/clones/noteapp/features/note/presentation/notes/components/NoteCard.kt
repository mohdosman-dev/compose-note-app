package com.otaku.clones.noteapp.features.note.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.otaku.clones.noteapp.features.note.domain.model.Note
import java.util.*

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    note: Note,
    cutCornerSize: Dp = 30.dp,
    onDeleteNote: () -> Unit,
) {
    Box(modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(note.color, 0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cornerRadius.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onSurface,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.body1,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onSurface,
            )
        }
        IconButton(
            onClick = onDeleteNote,
            modifier = modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete note"
            )
        }
    }
}

@Preview(name = "NoteCard")
@Composable
private fun PreviewNoteCard() {
    NoteCard(
        modifier = Modifier.size(20.dp, 20.dp),
        note = Note(
            title = "Title",
            content = "Content",
            color = 1,
            timestamp = Date().time,
            id = 1,
        ),
    ) {}
}