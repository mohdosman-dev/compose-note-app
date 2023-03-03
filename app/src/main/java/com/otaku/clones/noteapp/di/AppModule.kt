package com.otaku.clones.noteapp.di

import android.app.Application
import androidx.room.Room
import com.otaku.clones.noteapp.features.note.data.datasource.NoteDatabase
import com.otaku.clones.noteapp.features.note.data.repository.NoteRepository
import com.otaku.clones.noteapp.features.note.domain.repository.NoteRepositoryImpl
import com.otaku.clones.noteapp.features.note.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            insertNoteUseCase = InsertNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository),
        )
    }
}