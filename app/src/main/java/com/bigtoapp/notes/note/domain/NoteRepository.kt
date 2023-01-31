package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.domain.AllNotes

interface NoteRepository: AllNotes {

    suspend fun insertNote(noteData: NoteData)

    suspend fun updateNote(noteData: NoteData)
}