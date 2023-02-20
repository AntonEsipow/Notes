package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.data.NoteData

interface NoteRepository{

    suspend fun insertNote(noteData: NoteData)

    suspend fun updateNote(noteData: NoteData)
}