package com.bigtoapp.notes.note.sl

import com.bigtoapp.notes.main.sl.Core
import com.bigtoapp.notes.main.sl.Module
import com.bigtoapp.notes.note.domain.NoteDomainToData
import com.bigtoapp.notes.note.domain.NoteInteractor
import com.bigtoapp.notes.note.presentation.HandleNoteRequest
import com.bigtoapp.notes.note.presentation.NoteCommunications
import com.bigtoapp.notes.note.presentation.NoteUiStateCommunication
import com.bigtoapp.notes.note.presentation.NoteViewModel
import com.bigtoapp.notes.notes.data.BaseNotesRepository
import com.bigtoapp.notes.notes.domain.NoteDomainToUi
import com.bigtoapp.notes.notes.presentation.ShowListCommunication

class NoteModule(private val core: Core): Module<NoteViewModel> {

    override fun viewModel(): NoteViewModel {

        val repository = BaseNotesRepository(
            core.provideDatabase().notesDao()
        )

        return NoteViewModel(
            core.provideNoteEditOptions(),
            core,
            NoteCommunications.Base(
                core.provideNotesListCommunication(),
                NoteUiStateCommunication.Base()
            ),
            NoteInteractor.Base(repository),
            HandleNoteRequest(
                core.provideDispatchers(),
                ShowListCommunication.Base(
                    core.provideNotesListCommunication()
                ),
                NoteDomainToUi()
            )
        )
    }
}