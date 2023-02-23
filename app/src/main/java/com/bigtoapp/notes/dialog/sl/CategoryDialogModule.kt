package com.bigtoapp.notes.dialog.sl

import com.bigtoapp.notes.categories.domain.CategoryDomainToUi
import com.bigtoapp.notes.category.presentation.CategoryCommunications
import com.bigtoapp.notes.dialog.domain.SelectCategoryInteractor
import com.bigtoapp.notes.dialog.presentation.*
import com.bigtoapp.notes.main.sl.Module
import com.bigtoapp.notes.main.sl.Core

class CategoryDialogModule(
    private val core: Core
): Module<SelectCategoryViewModel> {

    override fun viewModel(): SelectCategoryViewModel {

        val communications = SelectCategoryCommunications.Base(
            ProgressSelectCategoryCommunication.Base(),
            SelectCategoryUiStateCommunication.Base(),
            core.provideCategoriesListCommunication()
        )

        return SelectCategoryViewModel(
            SelectCategoryInteractor.Base(
                core.provideCategoriesRepository()
            ),
            communications,
            core.provideNoteCommunications(),
            core.provideSelectedCategory(),
            HandleSelectCategoryRequest(
                core.provideDispatchers(),
                communications,
                CategoryDomainToUi(),
                SelectDomainCategoryState(
                    communications
                )
            )
        )
    }
}