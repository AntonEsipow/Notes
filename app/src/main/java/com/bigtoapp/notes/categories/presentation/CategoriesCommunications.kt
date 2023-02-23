package com.bigtoapp.notes.categories.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.communications.MutableObserve
import com.bigtoapp.notes.main.communications.MutableShow
import com.bigtoapp.notes.main.presentation.Communication
import com.bigtoapp.notes.notes.presentation.*

interface MutableCategoriesCommunications: MutableObserve<CategoryUi, CategoriesUiState>,
    MutableShow<CategoryUi, CategoriesUiState>

class CategoriesCommunications(
    private val progress: ProgressCategoryCommunication,
    private val state: CategoriesUiStateCommunication,
    private val categoryList: CategoriesListCommunication
): MutableCategoriesCommunications{

    override fun showProgress(show: Int) = progress.put(show)

    override fun showList(showList: List<CategoryUi>) = categoryList.put(showList)

    override fun showState(showState: CategoriesUiState) = state.put(showState)

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        progress.observe(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<CategoriesUiState>) =
        state.observe(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<CategoryUi>>) =
        categoryList.observe(owner, observer)
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