package com.bigtoapp.notes.category.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.categories.presentation.CategoriesListCommunication
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.main.presentation.Communication
import com.bigtoapp.notes.note.presentation.GetList
import com.bigtoapp.notes.note.presentation.ObserveNote

interface CategoryCommunications: ObserveCategory, GetList<CategoryUi> {

    fun showState(uiState: CategoryUiState)

    class Base(
        private val noteList: CategoriesListCommunication,
        private val state: CategoryUiStateCommunication
    ): CategoryCommunications {

        override fun showState(uiState: CategoryUiState) = state.put(uiState)

        override fun observeState(owner: LifecycleOwner, observer: Observer<CategoryUiState>) =
            state.observe(owner, observer)

        override fun getList(): List<CategoryUi> = noteList.get()
    }
}

interface ObserveCategory {
    fun observeState(owner: LifecycleOwner, observer: Observer<CategoryUiState>)
}

interface CategoryUiStateCommunication: Communication.Mutable<CategoryUiState>{
    class Base: Communication.Post<CategoryUiState>(), CategoryUiStateCommunication
}