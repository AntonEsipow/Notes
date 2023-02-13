package com.bigtoapp.notes.notes.data

import com.bigtoapp.notes.main.data.ListRepository
import com.bigtoapp.notes.note.domain.NoteRepository
import com.bigtoapp.notes.notes.data.cache.NotesDao
import com.bigtoapp.notes.notes.domain.NoteDomain
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BaseNotesRepository(
    private val dao: NotesDao
): ListRepository<List<NoteDomain>>, NoteRepository {

    private val mutex = Mutex()

    override suspend fun insertNote(
        id: String, title: String, subtitle: String, createdTime: Long, performDate: Long
    ) =
        mutex.withLock {
        val data = NoteData(
            id = id,
            title = title,
            subtitle = subtitle,
            createdTime = createdTime,
            performDate = performDate
        )
        dao.insertNote(data)
    }

    override suspend fun updateNote(id: String, title: String, subtitle: String, performDate: Long) =
        mutex.withLock {
            dao.updateNote(id, title, subtitle, performDate)
        }


    override suspend fun all(): List<NoteDomain> = mutex.withLock {
        val data = dao.allNotes()
        return data.map { NoteDomain(it.id, it.title, it.subtitle, it.performDate) }
    }

    override suspend fun delete(id: String) = mutex.withLock{
        dao.deleteNote(id)
    }
}