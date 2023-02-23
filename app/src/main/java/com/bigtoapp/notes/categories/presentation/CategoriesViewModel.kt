package com.bigtoapp.notes.categories.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.main.communications.MutableObserve
import com.bigtoapp.notes.main.domain.ListInteractor
import com.bigtoapp.notes.main.presentation.*

class CategoriesViewModel(
    private val interactor: ListInteractor<List<CategoryDomain>>,
    private val communications: MutableCategoriesCommunications,
    private val handleRequest: HandleRequest<List<CategoryDomain>>,
    private val navigation: NavigationCommunication.Mutate
): ViewModel(), Init, MutableObserve<CategoryUi, CategoriesUiState>, CategoriesScreenOperations {

    override fun init() {
        handleRequest.handle(viewModelScope){
            interactor.all()
        }
    }

    override fun addCategory() = navigation.put(NavigationStrategy.ReplaceToBackStack(Screen.Category))

    override fun deleteCategory(categoryId: String) = handleRequest.handle(viewModelScope){
        interactor.delete(categoryId)
    }

    override fun editCategory(categoryId: String, color: Int) {
        navigation.put(NavigationStrategy.ReplaceWithCategoryBundle(Screen.Category, categoryId, color))
    }

    override fun navigateNotes() {
        navigation.put(NavigationStrategy.Replace(Screen.Notes))
    }

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        communications.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<CategoriesUiState>) =
        communications.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<CategoryUi>>) =
        communications.observeList(owner, observer)
}

interface CategoriesScreenOperations{
    fun addCategory()
    fun deleteCategory(categoryId: String)
    fun editCategory(categoryId: String, color: Int)
    fun navigateNotes()
}