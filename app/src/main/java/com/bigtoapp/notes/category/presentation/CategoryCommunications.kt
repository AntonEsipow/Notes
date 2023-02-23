package com.bigtoapp.notes.category.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.categories.presentation.CategoriesListCommunication
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.main.communications.ObserveState
import com.bigtoapp.notes.main.communications.ShowState
import com.bigtoapp.notes.main.presentation.Communication
import com.bigtoapp.notes.note.presentation.GetList

interface MutableCategoryCommunications: ObserveState<CategoryUiState>, GetList<CategoryUi>,
        ShowState<CategoryUiState>

class CategoryCommunications(
    private val categoryList: CategoriesListCommunication,
    private val state: CategoryUiStateCommunication
): MutableCategoryCommunications {

    override fun showState(showState: CategoryUiState) = state.put(showState)

    override fun observeState(owner: LifecycleOwner, observer: Observer<CategoryUiState>) =
        state.observe(owner, observer)

    override fun getList(): List<CategoryUi> = categoryList.get()
}

interface CategoryUiStateCommunication: Communication.Mutable<CategoryUiState>{
    class Base: Communication.Post<CategoryUiState>(), CategoryUiStateCommunication
}