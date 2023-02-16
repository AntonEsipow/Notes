package com.bigtoapp.notes.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.NavigationCommunication
import com.bigtoapp.notes.main.presentation.NavigationStrategy
import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.data.NoteWithCategoryData
import com.bigtoapp.notes.notes.domain.NoteDomain
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

    // todo refactor
    protected val noteDomain1 = NoteDomain(
        "1", "title", "subtitle", 1L, "1", "shop", 10
    )
    protected val noteUi1 = NoteUi(
        "1","title", "subtitle", "1","1", "shop", 10
    )
    protected val noteData1 = NoteData(
        "1", "title", "subtitle", 2L, 1L, "1"
    )
    private val categoryData1 = CategoryData(
        "1", "shop", 10
    )
    protected val noteWithCategoryData1 = NoteWithCategoryData(
        noteData1, categoryData1
    )


    protected val noteDomain2 = NoteDomain(
        "2", "shop", "apple", 2L, "2", "book", 11
    )
    protected val noteUi2 = NoteUi(
        "2", "shop", "apple", "2", "2", "book", 11
    )
    protected val noteData2 = NoteData(
        "2", "shop", "apple", 1L, 2L, "2"
    )
    private val categoryData2 = CategoryData(
        "2", "book", 11
    )
    protected val noteWithCategoryData2 = NoteWithCategoryData(
        noteData2, categoryData2
    )
    protected val noteDomain3 = NoteDomain(
        "3", "shop", "fish", 3L, "2", "book", 11
    )
}