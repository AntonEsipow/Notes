package com.bigtoapp.notes.notes.presentation

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.BaseTest
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.domain.NoteDomainToUi
import com.bigtoapp.notes.notes.domain.NotesInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NotesViewModelTest: BaseTest() {

    private lateinit var interactor: TestNotesInteractor
    private lateinit var communications: TestNotesCommunications
    private lateinit var viewModel: NotesViewModel

    @Before
    fun setUp(){
        communications = TestNotesCommunications()
        interactor = TestNotesInteractor()
        viewModel = NotesViewModel(
            interactor,
            TestNotesCommunications(),
            HandleNotesRequest.Base(
                TestDispatcherList(),
                communications,
                NoteDomainToUi()
            )
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
        assertEquals(0, communications.timesShowList)
    }

    @Test
    fun `test init note`() = runBlocking {
        interactor.changeExpectedList(
            listOf(NoteDomain(id = "1", title = "title", subtitle = "subtitle"))
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
            NoteUi(id = "1", header="title", description ="subtitle"),
            communications.notesList[0]
        )
    }

    @Test
    fun `test init some notes`() = runBlocking {
        interactor.changeExpectedList(
            listOf(
                NoteDomain(id = "1", title = "title", subtitle = "subtitle"),
                NoteDomain(id = "2", title = "shop", subtitle = "apple")
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
            NoteUi(id = "1", header="title", description ="subtitle"),
            communications.notesList[0]
        )
        assertEquals(
            NoteUi(id = "2", header="shop", description ="apple"),
            communications.notesList[1]
        )
    }

    @Test
    fun `test delete note`() = runBlocking {
        interactor.changeExpectedList(
            listOf(
                NoteDomain(id = "1", title = "title", subtitle = "subtitle"),
                NoteDomain(id = "2", title = "shop", subtitle = "apple")
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
            NoteUi(id = "1", header="title", description ="subtitle"),
            communications.notesList[0]
        )
        assertEquals(
            NoteUi(id = "2", header="shop", description ="apple"),
            communications.notesList[1]
        )

        interactor.changeExpectedList(
            listOf(
                NoteDomain(id = "2", title="shop", subtitle ="apple")
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
            NoteUi(id = "2", header="shop", description ="apple"),
            communications.notesList[0]
        )
    }

    @Test
    fun `test delete last note`() = runBlocking {
        interactor.changeExpectedList(
            listOf(
                NoteDomain(id = "1", title ="title", subtitle ="subtitle")
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
            NoteUi(id = "1", header="title", description ="subtitle"),
            communications.notesList[0]
        )

        interactor.changeExpectedList(emptyList())
        viewModel.deleteNote("1")

        assertEquals(View.VISIBLE, communications.progressCalledList[2])
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[3])
        assertEquals(2, communications.stateCalledList.size)

        assertEquals(NotesUiState.NoNotes, communications.stateCalledList[1])
        assertEquals(1, communications.timesShowList)
    }

    // todo check navigation strategy
    @Test
    fun `test edit note`() = Unit

    // todo check strategy when add navigation
    @Test
    fun `test navigation add note`() = Unit
}

private class TestNotesCommunications: NotesCommunications {

    val progressCalledList = mutableListOf<Int>()
    val stateCalledList = mutableListOf<NotesUiState>()
    val notesList = mutableListOf<NoteUi>()
    var timesShowList = 0

    override fun showProgress(show: Int){
        progressCalledList.add(show)
    }

    override fun showState(notesUiState: NotesUiState){
        stateCalledList.add(notesUiState)
    }

    override fun showList(list: List<NoteUi>) {
        timesShowList++
        notesList.clear()
        notesList.addAll(list)
    }

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) = Unit
    override fun observeState(owner: LifecycleOwner, observer: Observer<NotesUiState>) = Unit
    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NoteUi>>) = Unit
}

private class TestNotesInteractor: NotesInteractor {

    var initNotesCalledCount = 0
    val editNoteCalledList = mutableListOf<String>()
    val deleteNoteCalledList = mutableListOf<String>()

    private var notesList = mutableListOf<NoteDomain>()

    fun changeExpectedList(list: List<NoteDomain>) {
        notesList.clear()
        notesList.addAll(list)
    }

    override suspend fun allNotes(): List<NoteDomain>{
        initNotesCalledCount++
        return notesList
    }

    override suspend fun deleteNote(noteId: String): List<NoteDomain>{
        deleteNoteCalledList.add(noteId)
        return notesList
    }

    override fun editNote(noteId: String) {
        editNoteCalledList.add(noteId)
    }
}