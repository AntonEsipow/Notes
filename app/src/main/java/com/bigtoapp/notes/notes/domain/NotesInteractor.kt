package com.bigtoapp.notes.notes.domain

import com.bigtoapp.notes.note.data.NoteEditOptions

interface NotesInteractor {

    suspend fun allNotes(): List<NoteDomain>

    suspend fun deleteNote(noteId: String): List<NoteDomain>

    fun editNote(noteId: String)

    class Base(
        private val repository: NotesRepository,
        private val noteEdit: NoteEditOptions.Save
    ): NotesInteractor{

        override suspend fun allNotes() = repository.allNotes()

        override suspend fun deleteNote(noteId: String): List<NoteDomain> {
            repository.deleteNote(noteId)
            return repository.allNotes()
        }

        override fun editNote(noteId: String) = noteEdit.save(noteId)
    }
}