package com.bigtoapp.notes.category.sl

import com.bigtoapp.notes.category.domain.CategoryInteractor
import com.bigtoapp.notes.category.presentation.CategoryCommunications
import com.bigtoapp.notes.category.presentation.CategoryUiStateCommunication
import com.bigtoapp.notes.category.presentation.CategoryViewModel
import com.bigtoapp.notes.main.presentation.HandleRequest
import com.bigtoapp.notes.main.sl.Core
import com.bigtoapp.notes.main.sl.Module
import com.bigtoapp.notes.note.domain.Generate

class CategoryModule(private val core: Core): Module<CategoryViewModel> {

    override fun viewModel(): CategoryViewModel {

        val communications = CategoryCommunications(
            core.provideCategoriesListCommunication(),
            CategoryUiStateCommunication.Base()
        )

        return CategoryViewModel(
            core,
            communications,
            CategoryInteractor.Base(
                core.provideCategoriesRepository(),
                Generate.RandomId()
            ),
            HandleRequest.Base(
                core.provideDispatchers()
            ),
            core.provideNavigation()
        )
    }
}