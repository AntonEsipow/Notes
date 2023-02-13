package com.bigtoapp.notes.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.categories.presentation.CategoriesCommunications
import com.bigtoapp.notes.categories.presentation.CategoriesUiState
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.notes.presentation.DateFormatter
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.bigtoapp.notes.notes.presentation.NotesCommunications
import com.bigtoapp.notes.notes.presentation.NotesUiState

abstract class CategoriesBaseTest: BaseTest() {

    protected class TestCategoriesCommunications: CategoriesCommunications {

        val progressCalledList = mutableListOf<Int>()
        val stateCalledList = mutableListOf<CategoriesUiState>()
        val categoriesList = mutableListOf<CategoryUi>()
        var timesShowList = 0

        override fun showProgress(show: Int){
            progressCalledList.add(show)
        }

        override fun showState(uiState: CategoriesUiState){
            stateCalledList.add(uiState)
        }

        override fun showList(list: List<CategoryUi>) {
            timesShowList++
            categoriesList.clear()
            categoriesList.addAll(list)
        }

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) = Unit
        override fun observeState(owner: LifecycleOwner, observer: Observer<CategoriesUiState>) = Unit
        override fun observeList(owner: LifecycleOwner, observer: Observer<List<CategoryUi>>) = Unit
    }
}