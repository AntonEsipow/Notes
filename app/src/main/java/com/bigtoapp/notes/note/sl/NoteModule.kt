package com.bigtoapp.notes.note.sl

import android.app.DatePickerDialog
import com.bigtoapp.notes.main.sl.Core
import com.bigtoapp.notes.main.sl.Module
import com.bigtoapp.notes.note.domain.Generate
import com.bigtoapp.notes.note.domain.NoteInteractor
import com.bigtoapp.notes.note.presentation.*
import com.bigtoapp.notes.notes.domain.NoteDomainToUi
import com.bigtoapp.notes.notes.presentation.DateFormatter
import com.bigtoapp.notes.notes.presentation.DateToDomain
import com.bigtoapp.notes.notes.presentation.DateToUi
import com.bigtoapp.notes.notes.presentation.ShowNotesCommunications

class NoteModule(private val core: Core): Module<NoteViewModel> {

    override fun viewModel(): NoteViewModel {

        val formatter = DateToUi()

        return NoteViewModel(
            core.provideNoteEditOptions(),
            core,
            NoteCommunications.Base(
                core.provideNotesListCommunication(),
                NoteUiStateCommunication.Base()
            ),
            NoteInteractor.Base(
                core.provideRepository(),
                Generate.CurrentTime(),
                DateToDomain(
                    Generate.CalendarTime(formatter)
                )
            ),
            HandleNoteRequest(
                core.provideDispatchers()
            ),
            core.provideNavigation(),
            formatter,
            Dialog.DatePicker(core)
        )
    }
}