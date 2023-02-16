package com.bigtoapp.notes.note.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.NotesBaseTest
import com.bigtoapp.notes.main.presentation.HandleRequest
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.main.presentation.NavigationStrategy
import com.bigtoapp.notes.note.domain.NoteInteractor
import com.bigtoapp.notes.notes.presentation.NoteUi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteViewModelTest: NotesBaseTest() {

    private lateinit var interactor: TestNoteInteractor
    private lateinit var communications: TestNoteCommunications
    private lateinit var manageResources: TestManageResources
    private lateinit var viewModel: NoteViewModel
    private lateinit var navigation: TestNavigationCommunication

    @Before
    fun setUp(){
        navigation = TestNavigationCommunication()
        interactor = TestNoteInteractor()
        communications = TestNoteCommunications()
        manageResources = TestManageResources()
        val formatter = TestDateToUi()
        viewModel = NoteViewModel(
            manageResources,
            communications,
            interactor,
            HandleRequest.Base(
                TestDispatcherList()
            ),
            navigation,
            formatter,
            Dialog.DatePicker(manageResources)
        )
    }

    @Test
    fun `test display add note screen`() {

        viewModel.init(true, "")
        assertEquals(NoteUiState.AddNote, communications.stateCalledList[0])
    }

    @Test
    fun `test display update note screen`() {
        communications.changeExpectedList(listOf(noteUi1, noteUi2))

        viewModel.init(true, "2")
        assertEquals(
            NoteUiState.EditNote(noteUi2),
            communications.stateCalledList[0]
        )
    }

    @Test
    fun `test no title error state`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        communications.changeExpectedList(listOf(noteUi1, noteUi2))

        viewModel.init(true, "2")
        assertEquals(
            NoteUiState.EditNote(noteUi2),
            communications.stateCalledList[0]
        )
        viewModel.saveNote("", "tom sawyer", "1", "2", "2")
        assertEquals(NoteUiState.ShowErrorTitle("Enter something"), communications.stateCalledList[1])
    }

    @Test
    fun `test no description error state`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        communications.changeExpectedList(listOf(noteUi1, noteUi2))

        viewModel.init(true, "2")
        assertEquals(
            NoteUiState.EditNote(noteUi2),
            communications.stateCalledList[0]
        )
        viewModel.saveNote("book", "", "1", "2", "3")
        assertEquals(NoteUiState.ShowErrorDescription("Enter something"), communications.stateCalledList[1])
    }

    @Test
    fun `test insert note`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")

        viewModel.init(true, "")
        assertEquals(
            NoteUiState.AddNote,
            communications.stateCalledList[0]
        )

        viewModel.saveNote("book", "tom sawyer", "3", "", "3")
        assertEquals(1, interactor.insertNoteCalledCount)
        assertEquals(0, interactor.updateNoteCalledCount)

        assertEquals(
            NoteUiState.AddNote,
            communications.stateCalledList[1]
        )
    }

    @Test
    fun `test update note`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        communications.changeExpectedList(listOf(noteUi1, noteUi2))

        viewModel.init(true, "2")
        assertEquals(
            NoteUiState.EditNote(noteUi2),
            communications.stateCalledList[0]
        )

        viewModel.saveNote("book", "tom sawyer", "2", "2", "2")
        assertEquals(1, interactor.updateNoteCalledCount)
        assertEquals(0, interactor.insertNoteCalledCount)
        assertEquals("2", interactor.updateNoteIdCheck[0])

        assertEquals(1, navigation.count)
        assertEquals(NavigationStrategy.Back, navigation.strategy)
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

    override suspend fun insertNote(title: String, subtitle: String, date: String, categoryId: String){
        insertNoteCalledCount++
    }

    override suspend fun updateNote(noteId: String, title: String, subtitle: String, date: String, categoryId: String){
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

    override fun showState(uiState: NoteUiState){
        stateCalledList.add(uiState)
    }

    override fun getList(): List<NoteUi>{
        getNotesCalledCount++
        return noteList
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<NoteUiState>) = Unit
}

private class TestManageResources : ManageResources {
    private var string: String = ""

    fun makeExpectedAnswer(expected: String) {
        string = expected
    }

    override fun string(id: Int): String = string
}
