package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.presentation.DateFormatter
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteInteractorTest {

    private lateinit var interactor: NoteInteractor
    private lateinit var repository: TestNoteRepository
    private lateinit var generator: TestGenerator
    private lateinit var dateToDomain: TestDateToDomain

    @Before
    fun setUp(){
        generator = TestGenerator()
        repository = TestNoteRepository()
        dateToDomain = TestDateToDomain()
        interactor = NoteInteractor.Base(
            repository,
            generator,
            dateToDomain
        )
    }

    @Test
    fun `test insert notes`() = runBlocking {
        interactor.insertNote(
            InsertedDomainNote("buy", "milk", "2", "1")
        )
        assertEquals(1, repository.insertNoteCalledCount)
        assertEquals(0, repository.updateNoteCalledCount)
        assertEquals(
            NoteData("1", "buy", "milk", 1, 2, "1"),
            repository.insertNoteList[0]
        )

        interactor.insertNote(
            InsertedDomainNote("watch", "casio", "2", "1")
        )
        assertEquals(2, repository.insertNoteCalledCount)
        assertEquals(0, repository.updateNoteCalledCount)
        assertEquals(
            NoteData("2","watch", "casio", 2, 2, "1"),
            repository.insertNoteList[1]
        )
        assertEquals(2, repository.insertNoteList.size)

    }

    @Test
    fun `test update note`() = runBlocking {
        interactor.updateNote(
            UpdatedDomainNote("1","shop", "fish", "5", "1")
        )
        assertEquals(
            NoteData("1","shop", "fish", 1, 5, "1"),
            repository.updateNoteList[0]
        )
        assertEquals(1, repository.updateNoteCalledCount)
        assertEquals(0, repository.insertNoteCalledCount)
        assertEquals(1, repository.updateNoteList.size)
    }

    @Test
    fun `test insert note no date`() = runBlocking {
        interactor.insertNote(
            InsertedDomainNote("watch", "casio", "", "1")
        )
        assertEquals(
            NoteData("1","watch", "casio", 1, 1, "1"),
            repository.insertNoteList[0]
        )
        assertEquals(1, repository.insertNoteCalledCount)
        assertEquals(0, repository.updateNoteCalledCount)
        assertEquals(1, repository.insertNoteList.size)
    }
}

private class TestNoteRepository: NoteRepository{

    var insertNoteCalledCount = 0
    var updateNoteCalledCount = 0

    val insertNoteList = mutableListOf<NoteData>()
    val updateNoteList = mutableListOf<NoteData>()

    override suspend fun insertNote(noteData: NoteData) {
        insertNoteCalledCount++
        insertNoteList.add(noteData)
    }

    override suspend fun updateNote(noteData: NoteData) {
        updateNoteCalledCount++
        updateNoteList.add(noteData)
    }
}

private class TestGenerator: Generate.Mutable<Long> {

    private var id = 0
    private var time = 0L

    override fun generateId(): String {
        ++id
        return id.toString()
    }

    override fun generateTime(): Long = ++time
}

private class TestDateToDomain: DateFormatter<Long, String> {

    private var date = 0L
    val dateCalledList = mutableListOf<Long>()

    override fun format(value: String): Long {
        return if(value.isEmpty()) {
            dateCalledList.add(date)
            ++date
        }
        else
            value.toLong()
    }
}