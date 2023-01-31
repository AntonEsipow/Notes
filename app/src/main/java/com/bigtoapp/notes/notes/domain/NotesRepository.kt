package com.bigtoapp.notes.notes.domain

import com.bigtoapp.notes.note.domain.NoteRepository

interface NotesRepository: AllNotes {

    suspend fun deleteNote(noteId: String)
}

interface AllNotes{
    suspend fun allNotes(): List<NoteDomain>
}