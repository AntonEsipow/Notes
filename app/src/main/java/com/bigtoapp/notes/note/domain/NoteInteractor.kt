package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.domain.NoteDomain
import java.util.*

interface NoteInteractor {

    suspend fun insertNote(title: String, subtitle: String): List<NoteDomain>

    suspend fun updateNote(noteId: String, title: String, subtitle: String): List<NoteDomain>

    class Base(
        private val repository: NoteRepository
    ):NoteInteractor {

        override suspend fun insertNote(title: String, subtitle: String): List<NoteDomain> {
            val noteId = UUID.randomUUID().toString()
            repository.insertNote(noteId, title, subtitle)
            return repository.allNotes()
        }

        override suspend fun updateNote(
            noteId: String,
            title: String,
            subtitle: String
        ): List<NoteDomain> {
            repository.updateNote(noteId, title, subtitle)
            return repository.allNotes()
        }
    }
}