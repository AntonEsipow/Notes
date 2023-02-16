package com.bigtoapp.notes.notes.data

import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.main.NotesBaseTest
import com.bigtoapp.notes.notes.domain.NoteDomain
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseNotesRepositoryTest: NotesBaseTest() {

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
    fun `test all notes with category`(): Unit = runBlocking {
        dao.changeExpectedNoteWithCategoryData(listOf(
            noteWithCategoryData1, noteWithCategoryData2
        ))
        val actual = repository.all()
        val expected = listOf(
            noteDomain1, noteDomain2
        )
        assertEquals(expected, actual)
        assertEquals(1, dao.allNotesWithCategoryCalledCount)
    }

    @Test
    fun `test insert note`(): Unit = runBlocking{
        dao.changeExpectedNoteWithCategoryData(listOf(
            noteWithCategoryData1, noteWithCategoryData2
        ))
        repository.insertNote("3", "shop", "fish", 1L, 3L, "1")
        assertEquals(1, dao.insertNoteCalledCount)

        val actual = repository.all()
        val expected = listOf(
            noteDomain1, noteDomain2, noteDomain3
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test update note`(): Unit = runBlocking{
        dao.changeExpectedNoteWithCategoryData(listOf(
            noteWithCategoryData1, noteWithCategoryData2
        ))
        repository.updateNote("2", "shop", "fish", 4L, "1")
        assertEquals(1, dao.updateNoteCalledCount)

        val actual = repository.all()
        val expected = listOf(
            noteDomain1,
            noteDomain2
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test delete note`(): Unit = runBlocking{
        dao.changeExpectedNoteWithCategoryData(listOf(
            noteWithCategoryData1, noteWithCategoryData2
        ))
        repository.delete("1")
        assertEquals("1", dao.deleteNoteCalledList[0])

        val actual = repository.all()
        val expected = listOf(
            noteDomain2
        )
        assertEquals(expected, actual)
    }
}

private class TestNotesDao: NotesDao {

    var allNotesCalledCount = 0
    var updateNoteCalledCount = 0
    var insertNoteCalledCount = 0
    val deleteNoteCalledList = mutableListOf<String>()

    var allNotesWithCategoryCalledCount = 0

    val noteWithCategoryData = mutableListOf<NoteWithCategoryData>()

    fun changeExpectedNoteWithCategoryData(newData: List<NoteWithCategoryData>): Unit = with(noteWithCategoryData) {
        clear()
        addAll(newData)
    }

    override fun insertNote(noteData: NoteData) {
        insertNoteCalledCount++
        noteWithCategoryData.add(NoteWithCategoryData(noteData, categoryData2))
    }

    override fun updateNote(id: String, title: String, subtitle: String, performDate: Long, categoryId: String) {
        updateNoteCalledCount++
        val item = noteWithCategoryData.find { it.noteData.mapId(id) }
        val index = noteWithCategoryData.indexOf(item)
        val noteData = noteWithCategoryData2
        noteWithCategoryData[index] = noteData
    }

    override fun allNotes(): List<NoteData> {
        allNotesCalledCount++
        return listOf()
    }

    override fun deleteNote(noteId: String) {
        deleteNoteCalledList.add(noteId)
        val item = noteWithCategoryData.find { it.noteData.mapId(noteId) }
        noteWithCategoryData.remove(item)
    }

    override fun allNotesWithCategory(): List<NoteWithCategoryData> {
        allNotesWithCategoryCalledCount++
        return noteWithCategoryData
    }

    protected val noteData2 = NoteData(
        "2", "shop", "apple", 1L, 2L, "2"
    )
    private val categoryData2 = CategoryData(
        "2", "book", 11
    )
    protected val noteWithCategoryData2 = NoteWithCategoryData(
        noteData2, categoryData2
    )
}