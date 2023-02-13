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
        interactor.insertNote("shop", "fish", "3")
        assertEquals(1, repository.insertNoteCalledCount)
        assertEquals(0, repository.updateNoteCalledCount)
        assertEquals("1", repository.idCalledList[0])
        assertEquals(1, repository.createdTimeCalledList[0])
        assertEquals(3, repository.insertDateCalledList[0])

        interactor.insertNote("watch", "casio", "2")
        assertEquals(2, repository.insertNoteCalledCount)
        assertEquals(0, repository.updateNoteCalledCount)
        assertEquals("2", repository.idCalledList[1])
        assertEquals(2, repository.createdTimeCalledList[1])
        assertEquals(2, repository.insertDateCalledList[1])
    }

    @Test
    fun `test update note`() = runBlocking {
        interactor.updateNote("1","shop", "fish", "5")
        assertEquals(1, repository.updateNoteCalledCount)
        assertEquals(0, repository.insertNoteCalledCount)
        assertEquals(5, repository.updateDateCalledList[0])
    }

    @Test
    fun `test insert note no date`() = runBlocking {
        interactor.insertNote("shop", "fish", "")
        assertEquals(1, repository.insertNoteCalledCount)
        assertEquals(0, repository.updateNoteCalledCount)
        assertEquals("1", repository.idCalledList[0])
        assertEquals(1, repository.createdTimeCalledList[0])
        assertEquals(1, repository.insertDateCalledList[0])
    }
}

private class TestNoteRepository: NoteRepository{

    var insertNoteCalledCount = 0
    var updateNoteCalledCount = 0

    private var insertDate = 0L
    private var updateDate = 0L
    val insertDateCalledList = mutableListOf<Long>()
    val updateDateCalledList = mutableListOf<Long>()

    val idCalledList = mutableListOf<String>()
    val createdTimeCalledList = mutableListOf<Long>()

    override suspend fun insertNote(
        id: String, title: String, subtitle: String, createdTime: Long, performDate: Long
    ) {
        insertNoteCalledCount++
        idCalledList.add(id)
        createdTimeCalledList.add(createdTime)
        insertDate = performDate
        insertDateCalledList.add(insertDate)
    }

    override suspend fun updateNote(
        id: String, title: String, subtitle: String, performDate: Long
    ) {
        updateNoteCalledCount++
        updateDate = performDate
        updateDateCalledList.add(updateDate)
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