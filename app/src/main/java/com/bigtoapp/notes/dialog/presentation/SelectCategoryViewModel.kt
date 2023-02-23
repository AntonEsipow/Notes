package com.bigtoapp.notes.dialog.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.categories.presentation.MapCategoryName
import com.bigtoapp.notes.dialog.domain.SelectCategoryInteractor
import com.bigtoapp.notes.main.communications.MutableObserve
import com.bigtoapp.notes.main.communications.ShowState
import com.bigtoapp.notes.main.presentation.HandleRequest
import com.bigtoapp.notes.main.presentation.SingleInit
import com.bigtoapp.notes.note.presentation.NoteUiState

class SelectCategoryViewModel(
    private val interactor: SelectCategoryInteractor,
    private val communications: MutableSelectCommunications,
    private val noteCommunications: ShowState<NoteUiState>,
    private val selectedCategory: SelectedCategoryCommunications,
    private val handleRequest: HandleRequest<List<CategoryDomain>>
): ViewModel(), SingleInit, MutableObserve<CategoryUi, SelectCategoryUiState>, SetCategory {

    override fun singleInit(isFirstRun: Boolean) {
        if(isFirstRun){
            handleRequest.handle(viewModelScope){
                interactor.allCategories()
            }
        }
    }

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        communications.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<SelectCategoryUiState>) =
        communications.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<CategoryUi>>) =
        communications.observeList(owner, observer)

    override fun setSelectedCategory(category: CategoryUi) {
        selectedCategory.setSelectedCategory(category)
        val categoryName = category.map(MapCategoryName())
        noteCommunications.showState(NoteUiState.EditCategory(categoryName))
    }
}

interface SetCategory{
    fun setSelectedCategory(category : CategoryUi)
}