package com.otaku.clones.noteapp.features.note.presentation.notes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DefaultRadioButton(
    title: String,
    onSelect: () -> Unit,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary,
                unselectedColor = MaterialTheme.colors.onBackground,
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
        )
    }
}
