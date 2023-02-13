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
        interactor = NotesInteractor(
            repository,
            NoteEditOptions()
        )
    }

    @Test
    fun `test all notes`() = runBlocking {
        repository.changeExpectedList(
            listOf(
                NoteDomain(id = "1", title = "title", subtitle = "subtitle", 2L),
                NoteDomain(id = "2", title = "book", subtitle = "description", 2L)
            )
        )
        val actual = interactor.all()
        val expected = listOf(
            NoteDomain(id = "1", title = "title", subtitle = "subtitle", 2L),
            NoteDomain(id = "2", title = "book", subtitle = "description", 2L)
        )
        assertEquals(expected, actual)
        assertEquals(1, repository.allNotesCalledCount)
    }

    @Test
    fun `test delete note`() = runBlocking {
        interactor.delete("1")
        assertEquals(1, repository.allNotesCalledCount)
        assertEquals("1", repository.deleteNoteCalledList[0])
    }
}

private class TestNotesRepository: NotesRepository {

    var allNotesCalledCount = 0
    val deleteNoteCalledList = mutableListOf<String>()

    private val notesList = mutableListOf<NoteDomain>()

    fun changeExpectedList(list: List<NoteDomain>) = with(notesList){
        clear()
        addAll(list)
    }

    override suspend fun allNotes(): List<NoteDomain>{
        allNotesCalledCount++
        return notesList
    }

    override suspend fun deleteNote(noteId: String) {
        deleteNoteCalledList.add(noteId)
    }
}