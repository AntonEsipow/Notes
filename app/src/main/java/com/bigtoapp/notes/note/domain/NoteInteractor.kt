package com.bigtoapp.notes.note.domain

interface NoteInteractor {

    suspend fun insertNote(title: String, subtitle: String)

    suspend fun updateNote(noteId: String, title: String, subtitle: String)
}