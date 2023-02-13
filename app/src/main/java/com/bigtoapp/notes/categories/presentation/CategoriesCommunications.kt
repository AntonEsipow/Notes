package com.bigtoapp.notes.categories.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.presentation.Communication
import com.bigtoapp.notes.notes.presentation.*

interface CategoriesCommunications: ObserveCategories {

    fun showProgress(show: Int)

    fun showList(list: List<CategoryUi>)

    fun showState(uiState: CategoriesUiState)

    class Base(
        private val progress: ProgressCategoryCommunication,
        private val state: CategoriesUiStateCommunication,
        private val categoryList: CategoriesListCommunication
    ): CategoriesCommunications{

        override fun showProgress(show: Int) = progress.put(show)

        override fun showList(list: List<CategoryUi>) = categoryList.put(list)

        override fun showState(uiState: CategoriesUiState) = state.put(uiState)

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
            progress.observe(owner, observer)

        override fun observeState(owner: LifecycleOwner, observer: Observer<CategoriesUiState>) =
            state.observe(owner, observer)

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<CategoryUi>>) =
            categoryList.observe(owner, observer)
    }
}

interface ObserveCategories {

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>)

    fun observeState(owner: LifecycleOwner, observer: Observer<CategoriesUiState>)

    fun observeList(owner: LifecycleOwner, observer: Observer<List<CategoryUi>>)
}

interface ProgressCategoryCommunication: Communication.Mutable<Int>{
    class Base: Communication.Post<Int>(), ProgressCategoryCommunication
}

interface CategoriesUiStateCommunication: Communication.Mutable<CategoriesUiState>{
    class Base: Communication.Post<CategoriesUiState>(), CategoriesUiStateCommunication
}

interface CategoriesListCommunication: Communication.Mutable<List<CategoryUi>>{
    class Base: Communication.Post<List<CategoryUi>>(), CategoriesListCommunication
}