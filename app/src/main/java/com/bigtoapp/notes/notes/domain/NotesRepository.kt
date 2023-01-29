package com.bigtoapp.notes.notes.domain

interface NotesRepository {

    suspend fun allNotes(): List<NoteDomain>

    suspend fun deleteNote(noteId: String)
}