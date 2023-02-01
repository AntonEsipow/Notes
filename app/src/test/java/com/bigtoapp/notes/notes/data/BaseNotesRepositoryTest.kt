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
            NoteData("1", "title", "description", 1L),
            NoteData("2", "book", "alice in wonderland", 1L)
        ))
        val actual = repository.allNotes()
        val expected = listOf(
            NoteDomain("1", "title", "description"),
            NoteDomain("2", "book", "alice in wonderland")
        )
        assertEquals(expected, actual)
        assertEquals(1, dao.allNotesCalledCount)
    }

    @Test
    fun `test insert note`(): Unit = runBlocking{
        dao.changeExpectedData(listOf(
            NoteData("1", "title", "description", 1L),
            NoteData("2", "book", "alice in wonderland", 1L)
        ))
        repository.insertNote("3", "shop", "fish")
        assertEquals(1, dao.insertNoteCalledCount)

        val actual = repository.allNotes()
        val expected = listOf(
            NoteDomain("1", "title", "description"),
            NoteDomain("2", "book", "alice in wonderland"),
            NoteDomain("3", "shop", "fish")
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test update note`(): Unit = runBlocking{
        dao.changeExpectedData(listOf(
            NoteData("1", "title", "description", 1L),
            NoteData("2", "book", "alice in wonderland", 1L)
        ))
        repository.updateNote("2", "shop", "fish")
        assertEquals(1, dao.updateNoteCalledCount)

        val actual = repository.allNotes()
        val expected = listOf(
            NoteDomain("1", "title", "description"),
            NoteDomain("2", "shop", "fish")
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test delete note`(): Unit = runBlocking{
        dao.changeExpectedData(listOf(
            NoteData("1", "title", "description", 1L),
            NoteData("2", "book", "alice in wonderland", 1L)
        ))
        repository.deleteNote("1")
        assertEquals("1", dao.deleteNoteCalledList[0])

        val actual = repository.allNotes()
        val expected = listOf(
            NoteDomain("2", "book", "alice in wonderland")
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

    override fun updateNote(id: String, title: String, subtitle: String) {
        updateNoteCalledCount++
        val item = data.find { it.mapId(id) }
        val index = data.indexOf(item)
        val noteData = NoteData(id, title, subtitle, 4L)
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