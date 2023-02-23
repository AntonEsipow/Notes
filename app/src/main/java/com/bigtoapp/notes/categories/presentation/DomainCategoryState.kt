package com.bigtoapp.notes.categories.presentation

import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.dialog.presentation.ShowScreenState
import com.bigtoapp.notes.main.communications.ShowState

class DomainCategoryState(
    private val communications: ShowState<CategoriesUiState>,
): ShowScreenState<List<CategoryDomain>> {
    override fun showState(state: List<CategoryDomain>) {
        communications.showState(
            if(state.isEmpty())
                CategoriesUiState.NoCategories
            else
                CategoriesUiState.Categories
        )
    }
}