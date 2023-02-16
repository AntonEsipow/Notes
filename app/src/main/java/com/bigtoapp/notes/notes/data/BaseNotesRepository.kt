package com.bigtoapp.notes.notes.data

import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.main.data.ListRepository
import com.bigtoapp.notes.note.domain.NoteRepository
import com.bigtoapp.notes.notes.domain.NoteDomain
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BaseNotesRepository(
    private val dao: NotesDao
): ListRepository<List<NoteDomain>>, NoteRepository {

    private val mutex = Mutex()

    override suspend fun insertNote(
        id: String, title: String, subtitle: String, createdTime: Long, performDate: Long, categoryId: String
    ) =
        mutex.withLock {
        val data = NoteData(
            id = id,
            title = title,
            subtitle = subtitle,
            createdTime = createdTime,
            performDate = performDate,
            categoryId = categoryId
        )
        dao.insertNote(data)
    }

    override suspend fun updateNote(
        id: String, title: String, subtitle: String, performDate: Long, categoryId: String
    ) = mutex.withLock {
            dao.updateNote(id, title, subtitle, performDate, categoryId)
        }

    // todo think refactor
    override suspend fun all(): List<NoteDomain> = mutex.withLock {
        val data = dao.allNotesWithCategory()
        return data.map {
            NoteDomain(
                it.noteData.id, it.noteData.title, it.noteData.subtitle, it.noteData.performDate,
                it.categoryData?.id ?: CategoryData.DEFAULT_CATEGORY_ID,
                it.categoryData?.title ?: CategoryData.DEFAULT_CATEGORY_NAME,
                it.categoryData?.color ?: CategoryData.DEFAULT_CATEGORY_COLOR
            )
        }
    }

    override suspend fun delete(id: String) = mutex.withLock{
        dao.deleteNote(id)
    }
}