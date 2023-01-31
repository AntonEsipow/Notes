package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.domain.NoteDomain
import java.util.*

interface NoteInteractor {

    suspend fun insertNote(title: String, subtitle: String): List<NoteDomain>

    suspend fun updateNote(noteId: String, title: String, subtitle: String): List<NoteDomain>

    class Base(
        private val repository: NoteRepository,
        private val mapper: NoteDomain.Mapper<NoteData>
    ):NoteInteractor {

        override suspend fun insertNote(title: String, subtitle: String): List<NoteDomain> {
            val note = NoteDomain(
                id = UUID.randomUUID().toString(),
                title = title,
                subtitle = subtitle
            )
            repository.insertNote(note.map(mapper))
            return repository.allNotes()
        }

        override suspend fun updateNote(
            noteId: String,
            title: String,
            subtitle: String
        ): List<NoteDomain> {
            val note = NoteDomain(
                id = noteId,
                title = title,
                subtitle = subtitle
            )
            repository.updateNote(note.map(mapper))
            return repository.allNotes()
        }
    }
}