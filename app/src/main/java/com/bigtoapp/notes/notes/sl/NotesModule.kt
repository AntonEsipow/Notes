package com.bigtoapp.notes.notes.sl

import com.bigtoapp.notes.main.sl.Core
import com.bigtoapp.notes.main.sl.Module
import com.bigtoapp.notes.notes.domain.NoteDomainToUi
import com.bigtoapp.notes.notes.domain.NotesInteractor
import com.bigtoapp.notes.notes.presentation.*

class NotesModule(private val core: Core): Module<NotesViewModel> {

    override fun viewModel(): NotesViewModel {

        val communications = NotesCommunications.Base(
            ProgressCommunication.Base(),
            NotesUiStateCommunication.Base(),
            core.provideNotesListCommunication()
        )

        return NotesViewModel(
            NotesInteractor(
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