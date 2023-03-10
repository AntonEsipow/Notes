package com.bigtoapp.notes.note.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.BaseTest
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.main.presentation.NavigationStrategy
import com.bigtoapp.notes.note.data.NoteEditOptions
import com.bigtoapp.notes.note.domain.NoteInteractor
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.domain.NoteDomainToUi
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.bigtoapp.notes.notes.presentation.NotesUiState
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteViewModelTest: BaseTest() {

    private lateinit var interactor: TestNoteInteractor
    private lateinit var communications: TestNoteCommunications
    private lateinit var notesCommunications: TestNotesCommunications
    private lateinit var note: TestReadNote
    private lateinit var manageResources: TestManageResources
    private lateinit var viewModel: NoteViewModel
    private lateinit var navigation: TestNavigationCommunication

    @Before
    fun setUp(){
        navigation = TestNavigationCommunication()
        interactor = TestNoteInteractor()
        communications = TestNoteCommunications()
        note = TestReadNote()
        manageResources = TestManageResources()
        notesCommunications = TestNotesCommunications()
        val formatter = TestDateToUi()
        viewModel = NoteViewModel(
            note,
            manageResources,
            communications,
            interactor,
            HandleNoteRequest(
                TestDispatcherList(),
                notesCommunications,
                NoteDomainToUi(
                    formatter
                )
            ),
            navigation,
            formatter
        )
    }

    @Test
    fun `test display add note screen`() {
        note.changeExpectedValue("")

        viewModel.init(true)
        assertEquals(NoteUiState.AddNote, communications.stateCalledList[0])
    }

    @Test
    fun `test display update note screen`() {
        note.changeExpectedValue("2")
        communications.changeExpectedList(listOf(
            NoteUi("1", "title", "subtitle", "1"),
            NoteUi("2", "shop", "buy apples", "2")
        ))

        viewModel.init(true)
        assertEquals(
            NoteUiState.EditNote(NoteUi("2", "shop", "buy apples", "2")),
            communications.stateCalledList[0]
        )
    }

    @Test
    fun `test no title error state`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        note.changeExpectedValue("2")
        communications.changeExpectedList(listOf(
            NoteUi("1", "title", "subtitle", "1"),
            NoteUi("2", "shop", "buy apples", "2")
        ))

        viewModel.init(true)
        assertEquals(
            NoteUiState.EditNote(NoteUi("2", "shop", "buy apples", "2")),
            communications.stateCalledList[0]
        )
        viewModel.saveNote("", "tom sawyer", "1")
        assertEquals(NoteUiState.ShowErrorTitle("Enter something"), communications.stateCalledList[1])
    }

    @Test
    fun `test no description error state`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        note.changeExpectedValue("2")
        communications.changeExpectedList(listOf(
            NoteUi("1", "title", "subtitle", "1"),
            NoteUi("2", "shop", "buy apples", "2")
        ))

        viewModel.init(true)
        assertEquals(
            NoteUiState.EditNote(NoteUi("2", "shop", "buy apples", "2")),
            communications.stateCalledList[0]
        )
        viewModel.saveNote("book", "", "1")
        assertEquals(NoteUiState.ShowErrorDescription("Enter something"), communications.stateCalledList[1])
    }

    @Test
    fun `test insert note`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        note.changeExpectedValue("")

        viewModel.init(true)
        assertEquals(
            NoteUiState.AddNote,
            communications.stateCalledList[0]
        )
        interactor.changeExpectedList(listOf(
            NoteDomain("1", "title", "subtitle", 1L),
            NoteDomain("2", "shop", "buy apples", 2L),
            NoteDomain("3", "book", "tom sawyer", 3L)
        ))
        viewModel.saveNote("book", "tom sawyer", "3")
        assertEquals(1, interactor.insertNoteCalledCount)
        assertEquals(0, interactor.updateNoteCalledCount)
        assertEquals(1, notesCommunications.timesShowList)
        assertEquals(3, notesCommunications.notesList.size)
        assertEquals(NoteUi("3", "book", "tom sawyer", "3"), notesCommunications.notesList[2])
        assertEquals(
            NoteUiState.AddNote,
            communications.stateCalledList[1]
        )

        assertEquals(1, notesCommunications.stateCalledList.size)
        assertEquals(NotesUiState.Notes, notesCommunications.stateCalledList[0])
    }

    @Test
    fun `test update note`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        note.changeExpectedValue("2")
        communications.changeExpectedList(listOf(
            NoteUi("1", "title", "subtitle", "1"),
            NoteUi("2", "shop", "buy apples", "2")
        ))

        viewModel.init(true)
        assertEquals(
            NoteUiState.EditNote(NoteUi("2", "shop", "buy apples", "2")),
            communications.stateCalledList[0]
        )
        interactor.changeExpectedList(listOf(
            NoteDomain("1", "title", "subtitle", 1L),
            NoteDomain("2", "book", "tom sawyer", 2L)
        ))
        viewModel.saveNote("book", "tom sawyer", "2")
        assertEquals(1, interactor.updateNoteCalledCount)
        assertEquals(0, interactor.insertNoteCalledCount)
        assertEquals("2", interactor.updateNoteIdCheck[0])

        assertEquals(1, notesCommunications.timesShowList)
        assertEquals(2, notesCommunications.notesList.size)

        assertEquals(1, notesCommunications.stateCalledList.size)
        assertEquals(NotesUiState.Notes, notesCommunications.stateCalledList[0])

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

    private var notesList = mutableListOf<NoteDomain>()

    fun changeExpectedList(list: List<NoteDomain>) {
        notesList.clear()
        notesList.addAll(list)
    }

    override suspend fun insertNote(title: String, subtitle: String, date: String): List<NoteDomain>{
        insertNoteCalledCount++
        return notesList
    }

    override suspend fun updateNote(noteId: String, title: String, subtitle: String, date: String): List<NoteDomain>{
        updateNoteCalledCount++
        updateNoteIdCheck.add(noteId)
        return notesList
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

    override fun clear() {
        value = ""
    }
}

private class TestManageResources : ManageResources {
    private var string: String = ""

    fun makeExpectedAnswer(expected: String) {
        string = expected
    }

    override fun string(id: Int): String = string
}
