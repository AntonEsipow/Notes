package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.domain.NoteDomain
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteInteractorTest {

    private lateinit var interactor: NoteInteractor
    private lateinit var repository: TestNoteRepository

    @Before
    fun setUp(){
        repository = TestNoteRepository()
        interactor = NoteInteractor.Base(repository)
    }

    @Test
    fun `test insert note`() = runBlocking {
        interactor.insertNote("shop", "fish")
        assertEquals(1, repository.insertNoteCalledCount)
        assertEquals(1, repository.allNotesCalledCount)
    }

    @Test
    fun `test update note`() = runBlocking {
        interactor.updateNote("1","shop", "fish")
        assertEquals(1, repository.updateNoteCalledCount)
        assertEquals(1, repository.allNotesCalledCount)
    }
}

// todo think how to improve test
private class TestNoteRepository: NoteRepository{

    var allNotesCalledCount = 0
    var insertNoteCalledCount = 0
    var updateNoteCalledCount = 0

    override suspend fun insertNote(id: String, title: String, subtitle: String) {
        insertNoteCalledCount++
    }

    override suspend fun updateNote(id: String, title: String, subtitle: String) {
        updateNoteCalledCount++
    }

    override suspend fun allNotes(): List<NoteDomain> {
        allNotesCalledCount++
        return emptyList()
    }
}