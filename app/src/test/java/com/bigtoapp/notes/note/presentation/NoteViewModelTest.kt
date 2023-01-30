package com.bigtoapp.notes.note.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.BaseTest
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.note.data.NoteEditOptions
import com.bigtoapp.notes.note.domain.NoteInteractor
import com.bigtoapp.notes.notes.presentation.NoteUi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteViewModelTest: BaseTest() {

    private lateinit var interactor: TestNoteInteractor
    private lateinit var communications: TestNoteCommunications
    private lateinit var note: TestReadNote
    private lateinit var manageResources: TestManageResources
    private lateinit var viewModel: NoteViewModel

    @Before
    fun setUp(){
        interactor = TestNoteInteractor()
        communications = TestNoteCommunications()
        note = TestReadNote()
        manageResources = TestManageResources()
        viewModel = NoteViewModel(
            note,
            manageResources,
            communications,
            interactor,
            HandleNoteRequest.Base(
                TestDispatcherList()
            )
        )
    }

    @Test
    fun `test display add note screen`() {
        note.changeExpectedValue("")

        viewModel.displayScreenState()
        assertEquals(NoteUiState.AddNote, communications.stateCalledList[0])
    }

    @Test
    fun `test display update note screen`() {
        note.changeExpectedValue("2")
        communications.changeExpectedList(listOf(
            NoteUi("1", "title", "subtitle"),
            NoteUi("2", "shop", "buy apples")
        ))

        viewModel.displayScreenState()
        assertEquals(
            NoteUiState.EditNote(NoteUi("2", "shop", "buy apples")),
            communications.stateCalledList[0]
        )
    }

    @Test
    fun `test no title error state screen`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        note.changeExpectedValue("2")
        communications.changeExpectedList(listOf(
            NoteUi("1", "title", "subtitle"),
            NoteUi("2", "shop", "buy apples")
        ))

        viewModel.displayScreenState()
        assertEquals(
            NoteUiState.EditNote(NoteUi("2", "shop", "buy apples")),
            communications.stateCalledList[0]
        )
        viewModel.saveNote("", "tom sawyer")
        assertEquals(NoteUiState.ShowError("Enter something"), communications.stateCalledList[1])
    }

    @Test
    fun `test no description error state screen`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        note.changeExpectedValue("2")
        communications.changeExpectedList(listOf(
            NoteUi("1", "title", "subtitle"),
            NoteUi("2", "shop", "buy apples")
        ))

        viewModel.displayScreenState()
        assertEquals(
            NoteUiState.EditNote(NoteUi("2", "shop", "buy apples")),
            communications.stateCalledList[0]
        )
        viewModel.saveNote("shop", "")
        assertEquals(NoteUiState.ShowError("Enter something"), communications.stateCalledList[1])
    }

    @Test
    fun `test insert note`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        note.changeExpectedValue("")

        viewModel.displayScreenState()
        assertEquals(
            NoteUiState.AddNote,
            communications.stateCalledList[0]
        )
        viewModel.saveNote("book", "tom sawyer")
        assertEquals(1, interactor.insertNoteCalledCount)
    }

    @Test
    fun `test update note`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        note.changeExpectedValue("2")
        communications.changeExpectedList(listOf(
            NoteUi("1", "title", "subtitle"),
            NoteUi("2", "shop", "buy apples")
        ))

        viewModel.displayScreenState()
        assertEquals(
            NoteUiState.EditNote(NoteUi("2", "shop", "buy apples")),
            communications.stateCalledList[0]
        )
        viewModel.saveNote("book", "tom sawyer")
        assertEquals(1, interactor.updateNoteCalledCount)
        assertEquals("2", interactor.updateNoteIdCheck[0])
    }

    @Test
    fun `test clear error`() {
        viewModel.clearError()

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is NoteUiState.ClearError)
    }

}

private class TestNoteInteractor: NoteInteractor {

    var insertNoteCalledCount = 0
    var updateNoteCalledCount = 0
    val updateNoteIdCheck = mutableListOf<String>()

    override suspend fun insertNote(title: String, subtitle: String){
        insertNoteCalledCount++
    }

    override suspend fun updateNote(noteId: String, title: String, subtitle: String){
        updateNoteCalledCount++
        updateNoteIdCheck.add(noteId)
    }
}

private class TestNoteCommunications: NoteCommunications{

    val stateCalledList = mutableListOf<NoteUiState>()
    var getNotesCalledCount = 0

    val noteList = mutableListOf<NoteUi>()

    fun changeExpectedList(list: List<NoteUi>){
        noteList.clear()
        noteList.addAll(list)
    }

    override fun showState(noteState: NoteUiState){
        stateCalledList.add(noteState)
    }

    override fun getNotesList(): List<NoteUi>{
        getNotesCalledCount++
        return noteList
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<NoteUiState>) = Unit
}

private class TestReadNote: NoteEditOptions.Read{

    private var value = ""

    fun changeExpectedValue(noteId: String){
        value = noteId
    }

    override fun read(): String = value
}

private class TestManageResources : ManageResources {
    private var string: String = ""

    fun makeExpectedAnswer(expected: String) {
        string = expected
    }

    override fun string(id: Int): String = string
}
