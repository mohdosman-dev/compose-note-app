package com.otaku.clones.noteapp.features.note.presentation.add_edit_note.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    isHintVisible: Boolean = true,
    onValueChanged: (value: String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    onFocusChange: (FocusState) -> Unit,
    singleLine: Boolean,
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValueChanged,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it) }
        )
        if (isHintVisible) {
            Text(
                text = hint,
                style = textStyle,
                color = Color.DarkGray
            )
        }
    }
}

//@Preview(name = "TrasparentHintTextField")
//@Composable
//private fun PreviewTrasparentHintTextField() {
//    TrasparentHintTextField()
//}