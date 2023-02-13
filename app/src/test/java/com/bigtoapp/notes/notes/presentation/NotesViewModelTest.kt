package com.bigtoapp.notes.notes.presentation

import android.view.View
import com.bigtoapp.notes.main.BaseTest
import com.bigtoapp.notes.main.presentation.NavigationStrategy
import com.bigtoapp.notes.main.presentation.Screen
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.domain.NoteDomainToUi
import com.bigtoapp.notes.notes.domain.NotesInteractor
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NotesViewModelTest: BaseTest() {

    private lateinit var interactor: TestNotesInteractor
    private lateinit var communications: TestNotesCommunications
    private lateinit var viewModel: NotesViewModel
    private lateinit var navigation: TestNavigationCommunication

    @Before
    fun setUp(){
        navigation = TestNavigationCommunication()
        communications = TestNotesCommunications()
        interactor = TestNotesInteractor()
        viewModel = NotesViewModel(
            interactor,
            TestNotesCommunications(),
            HandleNotesRequest(
                TestDispatcherList(),
                communications,
                NoteDomainToUi(
                    TestDateToUi()
                )
            ),
            navigation
        )
    }

    @Test
    fun `test init no notes`() = runBlocking {
        interactor.changeExpectedList(emptyList())
        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(1, interactor.initNotesCalledCount)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(NotesUiState.NoNotes, communications.stateCalledList[0])

        assertEquals(0, communications.notesList.size)
        assertEquals(1, communications.timesShowList)
    }

    @Test
    fun `test init note`() = runBlocking {
        interactor.changeExpectedList(
            listOf(NoteDomain("1", "title", "subtitle", 1L))
        )
        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(1, interactor.initNotesCalledCount)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(NotesUiState.Notes, communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(1, communications.notesList.size)
        assertEquals(
            NoteUi(id = "1", header="title", description ="subtitle", performDate = "1"),
            communications.notesList[0]
        )
    }

    @Test
    fun `test init some notes`() = runBlocking {
        interactor.changeExpectedList(
            listOf(
                NoteDomain("1", "title", "subtitle", 1L),
                NoteDomain("2", "shop", "apple", 2L)
            )
        )
        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(1, interactor.initNotesCalledCount)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(NotesUiState.Notes, communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(2, communications.notesList.size)
        assertEquals(
            NoteUi("1", "title", "subtitle", "1"),
            communications.notesList[0]
        )
        assertEquals(
            NoteUi("2", "shop", "apple", "2"),
            communications.notesList[1]
        )
    }

    @Test
    fun `test delete note`() = runBlocking {
        interactor.changeExpectedList(
            listOf(
                NoteDomain("1", "title", "subtitle", 1L),
                NoteDomain("2", "shop", "apple", 2L)
            )
        )
        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(1, interactor.initNotesCalledCount)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(NotesUiState.Notes, communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(2, communications.notesList.size)
        assertEquals(
            NoteUi("1", "title", "subtitle", "1"),
            communications.notesList[0]
        )
        assertEquals(
            NoteUi("2", "shop", "apple", "2"),
            communications.notesList[1]
        )

        interactor.changeExpectedList(
            listOf(
                NoteDomain("2", "shop", "apple", 1L)
            )
        )
        viewModel.deleteNote("1")

        assertEquals(View.VISIBLE, communications.progressCalledList[2])
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[3])
        assertEquals(2, communications.stateCalledList.size)

        assertEquals(1, interactor.deleteNoteCalledList.size)

        assertEquals(NotesUiState.Notes, communications.stateCalledList[1])
        assertEquals(
            NoteUi("2","shop", "apple", "1"),
            communications.notesList[0]
        )
    }

    @Test
    fun `test delete last note`() = runBlocking {
        interactor.changeExpectedList(
            listOf(
                NoteDomain("1", "title", "subtitle", 1L)
            )
        )
        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(1, interactor.initNotesCalledCount)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(NotesUiState.Notes, communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(1, communications.notesList.size)
        assertEquals(
            NoteUi("1", "title", "subtitle", "1"),
            communications.notesList[0]
        )

        interactor.changeExpectedList(emptyList())
        viewModel.deleteNote("1")

        assertEquals(View.VISIBLE, communications.progressCalledList[2])
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[3])
        assertEquals(2, communications.stateCalledList.size)

        assertEquals(NotesUiState.NoNotes, communications.stateCalledList[1])
        assertEquals(2, communications.timesShowList)
    }

    @Test
    fun `test navigation edit note`() {
        viewModel.editNote("12345")
        assertEquals("12345", interactor.updatedNoteId)

        assertEquals(1, navigation.count)
        assertEquals(NavigationStrategy.ReplaceToBackStack(Screen.Note), navigation.strategy)
    }

    @Test
    fun `test navigation add note`() {
        viewModel.addNote()

        assertEquals(1, navigation.count)
        assertEquals(NavigationStrategy.ReplaceToBackStack(Screen.Note), navigation.strategy)
    }
}

private class TestNotesInteractor: NotesInteractor {

    var updatedNoteId = ""

    var initNotesCalledCount = 0
    val editNoteCalledList = mutableListOf<String>()
    val deleteNoteCalledList = mutableListOf<String>()

    private var notesList = mutableListOf<NoteDomain>()

    fun changeExpectedList(list: List<NoteDomain>) {
        notesList.clear()
        notesList.addAll(list)
    }

    override suspend fun allNotes(): List<NoteDomain> {
        initNotesCalledCount++
        return notesList
    }

    override suspend fun deleteNote(noteId: String): List<NoteDomain>{
        deleteNoteCalledList.add(noteId)
        return notesList
    }

    override fun editNote(noteId: String) {
        updatedNoteId = noteId
        editNoteCalledList.add(noteId)
    }
}