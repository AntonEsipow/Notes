package com.bigtoapp.notes.categories.sl

import com.bigtoapp.notes.categories.domain.CategoriesInteractor
import com.bigtoapp.notes.categories.domain.CategoryDomainToUi
import com.bigtoapp.notes.categories.presentation.*
import com.bigtoapp.notes.main.sl.Core
import com.bigtoapp.notes.main.sl.Module

class CategoriesModule(private val core: Core): Module<CategoriesViewModel> {

    override fun viewModel(): CategoriesViewModel {

        val communication = CategoriesCommunications(
            ProgressCategoryCommunication.Base(),
            CategoriesUiStateCommunication.Base(),
            core.provideCategoriesListCommunication()
        )

        return CategoriesViewModel(
            CategoriesInteractor(
                core.provideCategoriesRepository()
            ),
            communication,
            HandleCategoriesRequest(
                core.provideDispatchers(),
                communication,
                CategoryDomainToUi(),
                DomainCategoryState(
                    communication
                )
            ),
            core.provideNavigation()
        )
    }
}