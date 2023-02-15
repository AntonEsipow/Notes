package com.bigtoapp.notes.category.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.presentation.SameCategory
import com.bigtoapp.notes.category.domain.CategoryInteractor
import com.bigtoapp.notes.main.presentation.*
import com.bigtoapp.notes.note.presentation.*

class CategoryViewModel(
    private val manageResources: ManageResources,
    private val communications: CategoryCommunications,
    private val interactor: CategoryInteractor,
    private val handleRequest: HandleRequest<Unit>,
    private val navigationCommunication: NavigationCommunication.Mutate
): ViewModel(), InitWithId, CategoryScreenOperations, ClearError, ObserveCategory {

    override fun init(isFirstRun: Boolean, id: String) {
        if(isFirstRun) {
            if(id.isEmpty())
                communications.showState(CategoryUiState.AddCategory)
            else {
                // we get liveData value that already exist to avoid going to DB again
                val categoryList = communications.getList()
                val mapper = SameCategory(id)
                val categoryDetails = categoryList.find { it.map(mapper) }!!
                communications.showState(CategoryUiState.EditCategory(categoryDetails))
            }
        }
    }

    override fun saveCategory(title: String, categoryId: String) {
        if(title.isEmpty())
            communications.showState(
                CategoryUiState.ShowError(manageResources.string(R.string.title_error_message)))
        else {
            if(categoryId.isEmpty()){
                handleRequest.handle(viewModelScope){
                    interactor.insertCategory(title)
                }
                communications.showState(CategoryUiState.AddCategory)
            } else {
                handleRequest.handle(viewModelScope){
                    interactor.updateCategory(categoryId, title)
                }
                navigationCommunication.put(NavigationStrategy.Back)
            }
        }
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<CategoryUiState>) =
        communications.observeState(owner, observer)

    override fun clearError() = communications.showState(CategoryUiState.ClearError())
}

interface CategoryScreenOperations{
    fun saveCategory(title: String, categoryId: String)
}