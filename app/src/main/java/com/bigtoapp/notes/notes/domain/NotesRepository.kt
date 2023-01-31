package com.bigtoapp.notes.notes.domain

interface NotesRepository: AllNotes {

    suspend fun deleteNote(noteId: String)
}

interface AllNotes{
    suspend fun allNotes(): List<NoteDomain>
}