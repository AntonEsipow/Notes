package com.bigtoapp.notes.notes.presentation

import com.bigtoapp.notes.dialog.presentation.ShowScreenState
import com.bigtoapp.notes.main.communications.ShowState
import com.bigtoapp.notes.notes.domain.NoteDomain

class DomainNotesState(
    private val communications: ShowState<NotesUiState>,
): ShowScreenState<List<NoteDomain>> {
    override fun showState(state: List<NoteDomain>) {
        communications.showState(
            if(state.isEmpty())
                NotesUiState.NoNotes
            else
                NotesUiState.Notes
        )
    }
}