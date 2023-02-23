package com.bigtoapp.notes.dialog.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.categories.presentation.*
import com.bigtoapp.notes.main.communications.MutableObserve
import com.bigtoapp.notes.main.communications.MutableShow
import com.bigtoapp.notes.main.presentation.Communication

interface MutableSelectCommunications: MutableObserve<CategoryUi, SelectCategoryUiState>,
    MutableShow<CategoryUi, SelectCategoryUiState>

class SelectCategoryCommunications(
    private val progress: ProgressSelectCategoryCommunication,
    private val state: SelectCategoryUiStateCommunication,
    private val categoryList: CategoriesListCommunication
): MutableSelectCommunications {

    override fun showProgress(show: Int) = progress.put(show)

    override fun showList(showList: List<CategoryUi>) = categoryList.put(showList)

    override fun showState(showState: SelectCategoryUiState) = state.put(showState)

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        progress.observe(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<SelectCategoryUiState>) =
        state.observe(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<CategoryUi>>) =
        categoryList.observe(owner, observer)
}

interface ProgressSelectCategoryCommunication: Communication.Mutable<Int>{
    class Base: Communication.Post<Int>(), ProgressSelectCategoryCommunication
}

interface SelectCategoryUiStateCommunication: Communication.Mutable<SelectCategoryUiState>{
    class Base: Communication.Post<SelectCategoryUiState>(), SelectCategoryUiStateCommunication
}