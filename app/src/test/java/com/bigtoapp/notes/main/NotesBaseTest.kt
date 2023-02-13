package com.bigtoapp.notes.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.NavigationCommunication
import com.bigtoapp.notes.main.presentation.NavigationStrategy
import com.bigtoapp.notes.notes.presentation.DateFormatter
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.bigtoapp.notes.notes.presentation.NotesCommunications
import com.bigtoapp.notes.notes.presentation.NotesUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

abstract class NotesBaseTest: BaseTest() {

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

    protected class TestDateToUi: DateFormatter<String, Long>{
        override fun format(value: Long): String = value.toString()
    }
}