package com.bigtoapp.notes.note.presentation

import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.communications.ShowState
import com.bigtoapp.notes.main.presentation.ManageResources

interface NoteErrorState{

    fun showError(title: String, subtitle: String)

    class Base(
        private val communication: ShowState<NoteUiState>,
        private val manageResources: ManageResources
    ): NoteErrorState{
        override fun showError(title: String, subtitle: String) {
            if(title.isEmpty())
                communication.showState(
                    NoteUiState.ShowErrorTitle(manageResources.string(R.string.title_error_message)))
            else if(subtitle.isEmpty())
                communication.showState(
                    NoteUiState.ShowErrorDescription(manageResources.string(R.string.subtitle_error_message)))
        }
    }
}