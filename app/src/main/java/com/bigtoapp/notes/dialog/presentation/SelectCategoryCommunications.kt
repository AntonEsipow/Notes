package com.bigtoapp.notes.dialog.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.categories.presentation.*
import com.bigtoapp.notes.main.presentation.Communication

interface SelectCategoryCommunications: ObserveSelectCategory {

    fun showProgress(show: Int)

    fun showList(list: List<CategoryUi>)

    fun showState(uiState: SelectCategoryUiState)

    class Base(
        private val progress: ProgressSelectCategoryCommunication,
        private val state: SelectCategoryUiStateCommunication,
        private val categoryList: CategoriesListCommunication
    ): SelectCategoryCommunications {

        override fun showProgress(show: Int) = progress.put(show)

        override fun showList(list: List<CategoryUi>) = categoryList.put(list)

        override fun showState(uiState: SelectCategoryUiState) = state.put(uiState)

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
            progress.observe(owner, observer)

        override fun observeState(owner: LifecycleOwner, observer: Observer<SelectCategoryUiState>) =
            state.observe(owner, observer)

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<CategoryUi>>) =
            categoryList.observe(owner, observer)
    }
}

interface ObserveSelectCategory {

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>)

    fun observeState(owner: LifecycleOwner, observer: Observer<SelectCategoryUiState>)

    fun observeList(owner: LifecycleOwner, observer: Observer<List<CategoryUi>>)
}

interface ProgressSelectCategoryCommunication: Communication.Mutable<Int>{
    class Base: Communication.Post<Int>(), ProgressSelectCategoryCommunication
}

interface SelectCategoryUiStateCommunication: Communication.Mutable<SelectCategoryUiState>{
    class Base: Communication.Post<SelectCategoryUiState>(), SelectCategoryUiStateCommunication
}