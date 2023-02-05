package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.domain.NoteDomain
import java.util.*

interface NoteInteractor {

    suspend fun insertNote(title: String, subtitle: String): List<NoteDomain>

    suspend fun updateNote(noteId: String, title: String, subtitle: String): List<NoteDomain>

    class Base(
        private val repository: NoteRepository,
        private val generate: GenerateData
    ):NoteInteractor {

        override suspend fun insertNote(title: String, subtitle: String): List<NoteDomain> {
            val noteId = generate.generateId()
            val createdTime = generate.generateCreatedTime()
            repository.insertNote(noteId, title, subtitle, createdTime)
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

interface GenerateData{

    fun generateId(): String
    fun generateCreatedTime(): Long

    class Base(): GenerateData{
        override fun generateId(): String = UUID.randomUUID().toString()

        override fun generateCreatedTime(): Long = System.currentTimeMillis()
    }
}