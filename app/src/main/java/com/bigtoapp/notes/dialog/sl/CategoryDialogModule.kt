package com.bigtoapp.notes.dialog.sl

import com.bigtoapp.notes.categories.domain.CategoryDomainToUi
import com.bigtoapp.notes.dialog.domain.SelectCategoryInteractor
import com.bigtoapp.notes.dialog.presentation.*
import com.bigtoapp.notes.main.sl.Module
import com.bigtoapp.notes.main.sl.Core
import com.bigtoapp.notes.note.presentation.NoteStateCommunication

class CategoryDialogModule(
    private val core: Core
): Module<SelectCategoryViewModel> {

    override fun viewModel(): SelectCategoryViewModel {

        val communications = SelectCategoryCommunications(
            ProgressSelectCategoryCommunication.Base(),
            SelectCategoryUiStateCommunication.Base(),
            SelectCategoryUiCommunication.Base()
        )

        return SelectCategoryViewModel(
            SelectCategoryInteractor.Base(
                core.provideCategoriesRepository()
            ),
            communications,
            communications,
            NoteStateCommunication(
                core.provideNoteStateCommunication(),
                core.provideNotesListCommunication()
            ),
            SelectedCategoryCommunications.Base(
                core.provideSelectedCategory()
            ),
            HandleSelectCategoryRequest(
                core.provideDispatchers(),
                communications,
                MapCategoryDomainToSelected(),
                SelectDomainCategoryState(
                    communications,
                    core
                )
            ),
            core
        )
    }
}