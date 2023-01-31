package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.domain.NoteDomain

interface NoteInteractor {

    suspend fun insertNote(title: String, subtitle: String): List<NoteDomain>

    suspend fun updateNote(noteId: String, title: String, subtitle: String): List<NoteDomain>
}