package com.bigtoapp.notes.dialog.presentation

import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.main.communications.MutableShow
import com.bigtoapp.notes.main.communications.ShowState

class SelectDomainCategoryState(
    private val communications: ShowState<SelectCategoryUiState>,
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