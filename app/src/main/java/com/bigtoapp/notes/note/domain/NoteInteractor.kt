package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.presentation.DateFormatter
import java.util.*

interface NoteInteractor {

    suspend fun insertNote(title: String, subtitle: String, date: String): List<NoteDomain>

    suspend fun updateNote(noteId: String, title: String, subtitle: String, date: String): List<NoteDomain>

    class Base(
        private val repository: NoteRepository,
        private val generate: Generate.Mutable<Long>,
        private val dateFormatter: DateFormatter<Long, String>
    ):NoteInteractor {

        override suspend fun insertNote(title: String, subtitle: String, date: String): List<NoteDomain> {
            val noteId = generate.generateId()
            val createdTime = generate.generateTime()
            // todo update repository fun
            repository.insertNote(noteId, title, subtitle, createdTime, dateFormatter.format(date))
            return repository.allNotes()
        }

        override suspend fun updateNote(
            noteId: String,
            title: String,
            subtitle: String,
            date: String
        ): List<NoteDomain> {
            // todo update repository fun
            repository.updateNote(noteId, title, subtitle, dateFormatter.format(date))
            return repository.allNotes()
        }
    }
}