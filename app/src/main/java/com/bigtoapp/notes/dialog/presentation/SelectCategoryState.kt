package com.bigtoapp.notes.dialog.presentation

import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.categories.presentation.CategoryUi

class SelectDomainCategoryState(
    private val communications: SelectCategoryCommunications,
): ShowScreenState<List<CategoryDomain>> {
    override fun showState(state: List<CategoryDomain>) {
        communications.showState(
            if(state.isEmpty())
                SelectCategoryUiState.DefaultCategory
            else
                SelectCategoryUiState.Categories
        )
    }
}