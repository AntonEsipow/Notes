package com.bigtoapp.notes.dialog.presentation

import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.main.communications.MutableShow
import com.bigtoapp.notes.main.communications.ShowState
import com.bigtoapp.notes.main.presentation.ManageResources

class SelectDomainCategoryState(
    private val communications: ShowState<SelectCategoryUiState>,
    private val manageResources: ManageResources
): ShowScreenState<List<CategoryDomain>> {
    override fun showState(state: List<CategoryDomain>) {
        communications.showState(
            if(state.isEmpty())
                SelectCategoryUiState.DefaultCategory(manageResources.string(R.string.set_default_category))
            else
                SelectCategoryUiState.Categories(manageResources.string(R.string.categories))
        )
    }
}