package com.bigtoapp.notes.note.sl

import com.bigtoapp.notes.main.presentation.HandleRequest
import com.bigtoapp.notes.main.sl.Core
import com.bigtoapp.notes.main.sl.Module
import com.bigtoapp.notes.dialog.presentation.SelectCategory
import com.bigtoapp.notes.dialog.presentation.SelectedCategoryCommunications
import com.bigtoapp.notes.note.domain.Generate
import com.bigtoapp.notes.note.domain.NoteInteractor
import com.bigtoapp.notes.note.presentation.*
import com.bigtoapp.notes.notes.presentation.DateToDomain
import com.bigtoapp.notes.notes.presentation.DateToUi

class NoteModule(private val core: Core): Module<NoteViewModel> {

    override fun viewModel(): NoteViewModel {

        val formatter = DateToUi()

        return NoteViewModel(
            core,
            NoteStateCommunication(
                core.provideNoteStateCommunication(),
                core.provideNotesListCommunication()
            ),
            SelectedCategoryCommunications.Base(
                core.provideSelectedCategory()
            ),
            NoteInteractor.Base(
                core.provideNotesRepository(),
                Generate.IdAndCurrentTime(),
                DateToDomain(
                    Generate.CalendarTime(formatter)
                )
            ),
            HandleRequest.Base(
                core.provideDispatchers()
            ),
            core.provideNavigation(),
            formatter,
            DatePicker(core),
            SelectCategory()
        )
    }
}