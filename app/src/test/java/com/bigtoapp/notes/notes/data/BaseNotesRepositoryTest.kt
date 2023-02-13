package com.bigtoapp.notes.notes.data

import com.bigtoapp.notes.notes.data.cache.NotesDao
import com.bigtoapp.notes.notes.domain.NoteDomain
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseNotesRepositoryTest {

    private lateinit var dao: TestNotesDao
    private lateinit var repository: BaseNotesRepository

    @Before
    fun setUp(){
        dao = TestNotesDao()
        repository = BaseNotesRepository(
            dao
        )
    }

    @Test
    fun `test all notes`(): Unit = runBlocking {
        dao.changeExpectedData(listOf(
            NoteData("1", "title", "description", 1L, 1L),
            NoteData("2", "book", "alice in wonderland", 1L, 2L)
        ))
        val actual = repository.all()
        val expected = listOf(
            NoteDomain("1", "title", "description", 1L),
            NoteDomain("2", "book", "alice in wonderland", 2L)
        )
        assertEquals(expected, actual)
        assertEquals(1, dao.allNotesCalledCount)
    }

    @Test
    fun `test insert note`(): Unit = runBlocking{
        dao.changeExpectedData(listOf(
            NoteData("1", "title", "description", 1L, 1L),
            NoteData("2", "book", "alice in wonderland", 1L, 2L)
        ))
        repository.insertNote("3", "shop", "fish", 1L, 3L)
        assertEquals(1, dao.insertNoteCalledCount)

        val actual = repository.all()
        val expected = listOf(
            NoteDomain("1", "title", "description", 1L),
            NoteDomain("2", "book", "alice in wonderland", 2L),
            NoteDomain("3", "shop", "fish", 3L)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test update note`(): Unit = runBlocking{
        dao.changeExpectedData(listOf(
            NoteData("1", "title", "description", 1L, 1L),
            NoteData("2", "book", "alice in wonderland", 1L, 2L)
        ))
        repository.updateNote("2", "shop", "fish", 4L)
        assertEquals(1, dao.updateNoteCalledCount)

        val actual = repository.all()
        val expected = listOf(
            NoteDomain("1", "title", "description", 1L),
            NoteDomain("2", "shop", "fish", 4L)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test delete note`(): Unit = runBlocking{
        dao.changeExpectedData(listOf(
            NoteData("1", "title", "description", 1L, 1L),
            NoteData("2", "book", "alice in wonderland", 1L, 2L)
        ))
        repository.delete("1")
        assertEquals("1", dao.deleteNoteCalledList[0])

        val actual = repository.all()
        val expected = listOf(
            NoteDomain("2", "book", "alice in wonderland", 2L)
        )
        assertEquals(expected, actual)
    }
}

private class TestNotesDao: NotesDao {

    var allNotesCalledCount = 0
    var updateNoteCalledCount = 0
    var insertNoteCalledCount = 0
    val deleteNoteCalledList = mutableListOf<String>()

    val data = mutableListOf<NoteData>()

    fun changeExpectedData(newData: List<NoteData>): Unit = with(data) {
        clear()
        addAll(newData)
    }

    override fun insertNote(noteData: NoteData) {
        insertNoteCalledCount++
        data.add(noteData)
    }

    override fun updateNote(id: String, title: String, subtitle: String, performDate: Long) {
        updateNoteCalledCount++
        val item = data.find { it.mapId(id) }
        val index = data.indexOf(item)
        val noteData = NoteData(id, title, subtitle, 4L, performDate)
        data[index] = noteData
    }

    override fun allNotes(): List<NoteData> {
        allNotesCalledCount++
        return data
    }

    override fun deleteNote(noteId: String) {
        deleteNoteCalledList.add(noteId)
        val item = data.find { it.mapId(noteId) }
        data.remove(item)
    }
}