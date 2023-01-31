package com.bigtoapp.notes.notes.domain

import com.bigtoapp.notes.note.data.NoteEditOptions
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NotesInteractorTest {

    private lateinit var repository: TestNotesRepository
    private lateinit var interactor: NotesInteractor

    @Before
    fun setUp(){
        repository = TestNotesRepository()
        interactor = NotesInteractor.Base(
            repository,
            NoteEditOptions.Base()
        )
    }

    @Test
    fun `test all notes`() = runBlocking {
        repository.changeExpectedList(
            listOf(
                NoteDomain(id = "1", title = "title", subtitle = "subtitle"),
                NoteDomain(id = "2", title = "book", subtitle = "description")
            )
        )
        val actual = interactor.allNotes()
        val expected = listOf(
            NoteDomain(id = "1", title = "title", subtitle = "subtitle"),
            NoteDomain(id = "2", title = "book", subtitle = "description")
        )
        assertEquals(expected, actual)
        assertEquals(1, repository.allNotesCalledCount)
    }

    @Test
    fun `test delete note`() = runBlocking {
        repository.changeExpectedList(
            listOf(
                NoteDomain(id = "1", title = "title", subtitle = "subtitle"),
                NoteDomain(id = "2", title = "book", subtitle = "description")
            )
        )
        repository.changeRemovedNote("1")
        val actual = interactor.deleteNote("1")
        val expected = listOf(NoteDomain(id = "2", title = "book", subtitle = "description"))
        assertEquals(expected, actual)
        assertEquals(1, repository.allNotesCalledCount)
        assertEquals("1", repository.deleteNoteCalledList[0])
    }
}

private class TestNotesRepository: NotesRepository {

    var allNotesCalledCount = 0
    val deleteNoteCalledList = mutableListOf<String>()

    private val notesList = mutableListOf<NoteDomain>()

    fun changeExpectedList(list: List<NoteDomain>) {
        notesList.clear()
        notesList.addAll(list)
    }

    // todo check in repository
    fun changeRemovedNote(noteId: String){
        val item = notesList.find { it.map(TestSameNoteMapper(noteId)) }
        notesList.remove(item)
    }

    override suspend fun allNotes(): List<NoteDomain>{
        allNotesCalledCount++
        return notesList
    }

    override suspend fun deleteNote(noteId: String) {
        deleteNoteCalledList.add(noteId)
    }
}

private class TestSameNoteMapper(private val noteId: String): NoteDomain.Mapper<Boolean>{
    override fun map(id: String, title: String, subtitle: String): Boolean = noteId == id
}