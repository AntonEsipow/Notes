package com.bigtoapp.notes.dialog.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.categories.presentation.MapCategoryName
import com.bigtoapp.notes.dialog.domain.SelectCategoryInteractor
import com.bigtoapp.notes.main.communications.MutableObserve
import com.bigtoapp.notes.main.communications.ShowState
import com.bigtoapp.notes.main.presentation.HandleRequest
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.main.presentation.SingleInit
import com.bigtoapp.notes.note.presentation.NoteUiState

class SelectCategoryViewModel(
    private val interactor: SelectCategoryInteractor,
    private val communications: MutableSelectCommunications,
    private val selectState: ShowState<SelectCategoryUiState>,
    private val noteCommunications: ShowState<NoteUiState>,
    private val selectedCategory: SelectedCategoryCommunications,
    private val handleRequest: HandleRequest<List<CategoryDomain>>,
    private val manageResources: ManageResources
): ViewModel(), SingleInit, MutableObserve<SelectedCategoryUi, SelectCategoryUiState>, SetCategory {

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

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<SelectedCategoryUi>>) =
        communications.observeList(owner, observer)

    override fun setSelectedCategory(category: SelectedCategoryUi) {
        selectedCategory.setSelectedCategory(category)
        selectState.showState(SelectCategoryUiState.SelectedCategory(category))
        val categoryColor = category.map(MapSelectedCategoryColor())
        val categoryName = category.map(MapSelectedCategoryName())
        noteCommunications.showState(NoteUiState.EditCategory(categoryName, categoryColor))
    }

    override fun setDefaultCategory() {
        selectState.showState(
            SelectCategoryUiState.DefaultCategory(manageResources.string(R.string.set_default_category))
        )
        selectedCategory.setSelectedCategory(CategoryData.getDefaultCategory())
        val category = CategoryData.getDefaultCategory()
        val categoryColor = category.map(MapSelectedCategoryColor())
        val categoryName = category.map(MapSelectedCategoryName())
        noteCommunications.showState(NoteUiState.EditCategory(categoryName, categoryColor))
    }
}

interface SetCategory{
    fun setSelectedCategory(category : SelectedCategoryUi)
    fun setDefaultCategory()
}