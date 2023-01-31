package com.bigtoapp.notes.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.bigtoapp.notes.notes.presentation.NotesCommunications
import com.bigtoapp.notes.notes.presentation.NotesUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

abstract class BaseTest {

    protected class TestDispatcherList(
        private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
    ): DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }

    protected class TestNotesCommunications: NotesCommunications {

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
}