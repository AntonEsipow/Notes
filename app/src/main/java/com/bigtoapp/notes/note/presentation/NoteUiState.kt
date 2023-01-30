package com.bigtoapp.notes.note.presentation

import com.bigtoapp.notes.notes.presentation.NoteUi

sealed class NoteUiState{

    // todo add fun apply for the ui

    object AddNote: NoteUiState()

    data class EditNote(private val noteUi: NoteUi): NoteUiState()

    abstract class AbstractError(
        private val message: String,
        private val errorEnabled: Boolean
    ): NoteUiState()

    data class ShowError(private val message: String): AbstractError(message, true)

    class ClearError : AbstractError("", false)
}
