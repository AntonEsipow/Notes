package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.domain.AllNotes

interface NoteRepository: AllNotes {

    suspend fun insertNote(id: String, title: String, subtitle: String, createdTime: Long)

    suspend fun updateNote(id: String, title: String, subtitle: String)
}