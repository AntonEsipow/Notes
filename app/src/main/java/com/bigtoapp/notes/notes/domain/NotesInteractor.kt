package com.bigtoapp.notes.notes.domain

interface NotesInteractor {

    suspend fun allNotes(): List<NoteDomain>

    suspend fun deleteNote(noteId: String): List<NoteDomain>

    fun editNote(noteId: String)


}