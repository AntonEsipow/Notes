package com.bigtoapp.notes.notes.data

import com.bigtoapp.notes.note.domain.NoteRepository
import com.bigtoapp.notes.notes.data.cache.NotesDao
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.domain.NotesRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BaseNotesRepository(
    private val dao: NotesDao
): NotesRepository, NoteRepository {

    private val mutex = Mutex()

    override suspend fun insertNote(id: String, title: String, subtitle: String) = mutex.withLock {
        val data = NoteData(
            id = id,
            title = title,
            subtitle = subtitle,
            createdTime = System.currentTimeMillis()
        )
        dao.insertNote(data)
    }

    override suspend fun updateNote(id: String, title: String, subtitle: String) = mutex.withLock {
        dao.updateNote(id, title, subtitle)
    }


    override suspend fun allNotes(): List<NoteDomain> = mutex.withLock {
        val data = dao.allNotes()
        return data.map { NoteDomain(it.id, it.title, it.subtitle) }
    }

    override suspend fun deleteNote(noteId: String) = mutex.withLock{
        dao.deleteNote(noteId)
    }
}