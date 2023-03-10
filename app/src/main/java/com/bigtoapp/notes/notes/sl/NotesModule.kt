package com.bigtoapp.notes.notes.sl

import com.bigtoapp.notes.main.sl.Core
import com.bigtoapp.notes.main.sl.Module
import com.bigtoapp.notes.notes.data.BaseNotesRepository
import com.bigtoapp.notes.notes.domain.NoteDomainToUi
import com.bigtoapp.notes.notes.domain.NotesInteractor
import com.bigtoapp.notes.notes.domain.NotesRepository
import com.bigtoapp.notes.notes.presentation.*

class NotesModule(private val core: Core): Module<NotesViewModel> {

    override fun viewModel(): NotesViewModel {

        val communications = NotesCommunications.Base(
            ProgressCommunication.Base(),
            core.provideNotesStateCommunication(),
            core.provideNotesListCommunication()
        )

        return NotesViewModel(
            NotesInteractor.Base(
                core.provideRepository(),
                core.provideNoteEditOptions()
            ),
            communications,
            HandleNotesRequest(
                core.provideDispatchers(),
                communications,
                NoteDomainToUi(DateToUi())
            ),
            core.provideNavigation()
        )
    }
}