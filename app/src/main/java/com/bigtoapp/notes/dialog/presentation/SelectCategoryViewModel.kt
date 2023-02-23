package com.bigtoapp.notes.dialog.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.categories.presentation.CategoriesCommunications
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.categories.presentation.MapCategoryName
import com.bigtoapp.notes.category.presentation.CategoryCommunications
import com.bigtoapp.notes.dialog.domain.SelectCategoryInteractor
import com.bigtoapp.notes.main.presentation.HandleRequest
import com.bigtoapp.notes.main.presentation.SingleInit
import com.bigtoapp.notes.note.presentation.GetList
import com.bigtoapp.notes.note.presentation.NoteCommunications
import com.bigtoapp.notes.note.presentation.NoteUiState

// todo refactor DI
class SelectCategoryViewModel(
    private val interactor: SelectCategoryInteractor,
    private val communications: SelectCategoryCommunications,
    private val noteCommunications: NoteCommunications,
    private val selectedCategory: SelectedCategoryCommunications,
    private val handleRequest: HandleRequest<List<CategoryDomain>>
): ViewModel(), SingleInit, ObserveSelectCategory, SetCategory {

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