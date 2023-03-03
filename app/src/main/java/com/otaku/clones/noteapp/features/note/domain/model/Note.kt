package com.otaku.clones.noteapp.features.note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.otaku.clones.noteapp.core.Constants.TABLE_NAME
import com.otaku.clones.noteapp.ui.theme.*

@Entity(tableName = TABLE_NAME)
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
    }
}

class InvalidNoteException(message: String) : java.lang.Exception(message)