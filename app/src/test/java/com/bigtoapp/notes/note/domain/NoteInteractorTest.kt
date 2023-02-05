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
    private lateinit var generator: TestGenerator

    @Before
    fun setUp(){
        generator = TestGenerator()
        repository = TestNoteRepository()
        interactor = NoteInteractor.Base(repository, generator)
    }

    @Test
    fun `test insert note`() = runBlocking {
        interactor.insertNote("shop", "fish")
        assertEquals(1, repository.insertNoteCalledCount)
        assertEquals(1, repository.allNotesCalledCount)
        assertEquals("1", repository.idCalledList[0])
        assertEquals(1, repository.createdTimeCalledList[0])

        interactor.insertNote("watch", "casio")
        assertEquals(2, repository.insertNoteCalledCount)
        assertEquals(2, repository.allNotesCalledCount)
        assertEquals("2", repository.idCalledList[1])
        assertEquals(2, repository.createdTimeCalledList[1])
    }

    @Test
    fun `test update note`() = runBlocking {
        interactor.updateNote("1","shop", "fish")
        assertEquals(1, repository.updateNoteCalledCount)
        assertEquals(1, repository.allNotesCalledCount)
    }
}

private class TestNoteRepository: NoteRepository{

    var allNotesCalledCount = 0
    var insertNoteCalledCount = 0
    var updateNoteCalledCount = 0

    val idCalledList = mutableListOf<String>()
    val createdTimeCalledList = mutableListOf<Long>()

    override suspend fun insertNote(id: String, title: String, subtitle: String, createdTime: Long) {
        insertNoteCalledCount++
        idCalledList.add(id)
        createdTimeCalledList.add(createdTime)
    }

    override suspend fun updateNote(id: String, title: String, subtitle: String) {
        updateNoteCalledCount++
    }

    override suspend fun allNotes(): List<NoteDomain> {
        allNotesCalledCount++
        return emptyList()
    }
}

private class TestGenerator: GenerateData {

    private var id = 0
    private var time = 0L

    override fun generateId(): String {
        ++id
        return id.toString()
    }

    override fun generateCreatedTime(): Long = ++time
}