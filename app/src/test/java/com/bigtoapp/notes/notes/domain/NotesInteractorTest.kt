package com.bigtoapp.notes.notes.domain

import com.bigtoapp.notes.main.NotesBaseTest
import com.bigtoapp.notes.main.data.ListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NotesInteractorTest: NotesBaseTest() {

    private lateinit var repository: TestNotesRepository
    private lateinit var interactor: NotesInteractor

    @Before
    fun setUp(){
        repository = TestNotesRepository()
        interactor = NotesInteractor(
            repository
        )
    }

    @Test
    fun `test all notes`() = runBlocking {
        repository.changeExpectedList(listOf(noteDomain1, noteDomain2))

        val actual = interactor.all()
        val expected = listOf(noteDomain1, noteDomain2)
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

private class TestNotesRepository: ListRepository<List<NoteDomain>> {

    var allNotesCalledCount = 0
    val deleteNoteCalledList = mutableListOf<String>()

    private val notesList = mutableListOf<NoteDomain>()

    fun changeExpectedList(list: List<NoteDomain>) = with(notesList){
        clear()
        addAll(list)
    }

    override suspend fun all(): List<NoteDomain>{
        allNotesCalledCount++
        return notesList
    }

    override suspend fun delete(id: String) {
        deleteNoteCalledList.add(id)
    }
}