package com.bigtoapp.notes.categories.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.categories.domain.CategoriesInteractor
import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.main.domain.ListInteractor
import com.bigtoapp.notes.main.presentation.*

class CategoriesViewModel(
    private val interactor: ListInteractor<List<CategoryDomain>>,
    private val communications: CategoriesCommunications,
    private val handleRequest: HandleRequest<List<CategoryDomain>>,
    private val navigation: NavigationCommunication.Mutate
): ViewModel(), Init, ObserveCategories, CategoriesScreenOperations {

    override fun init(isFirstRun: Boolean) {
        if(isFirstRun){
            handleRequest.handle(viewModelScope){
                interactor.all()
            }
        }
    }

    override fun addCategory() = navigation.put(NavigationStrategy.ReplaceToBackStack(Screen.Category))

    override fun deleteCategory(categoryId: String) = handleRequest.handle(viewModelScope){
        interactor.delete(categoryId)
    }

    override fun editCategory(categoryId: String) {
        interactor.edit(categoryId)
        navigation.put(NavigationStrategy.ReplaceToBackStack(Screen.Category))
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
    fun editCategory(categoryId: String)
}