package com.bigtoapp.notes.notes.domain

import com.bigtoapp.notes.note.domain.NoteRepository

interface NotesRepository {

    suspend fun deleteNote(noteId: String)

    suspend fun allNotes(): List<NoteDomain>
}