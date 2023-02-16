package com.bigtoapp.notes.note.domain

interface NoteRepository{

    suspend fun insertNote(
        id: String, title: String, subtitle: String, createdTime: Long, performDate: Long, categoryId: String
    )

    suspend fun updateNote(id: String, title: String, subtitle: String, performDate: Long, categoryId: String)
}