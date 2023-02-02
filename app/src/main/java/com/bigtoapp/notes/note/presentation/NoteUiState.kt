package com.bigtoapp.notes.note.presentation

import com.bigtoapp.notes.notes.presentation.NoteUi
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class NoteUiState{

    abstract fun apply(
        titleLayout: TextInputLayout,
        titleEditText: TextInputEditText,
        descriptionLayout: TextInputLayout,
        descriptionEditText: TextInputEditText
    )

    object AddNote: NoteUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText
        ) {
            titleEditText.setText("")
            descriptionEditText.setText("")
        }
    }

    data class EditNote(private val noteUi: NoteUi): NoteUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText
        ) {
            titleEditText.setText(noteUi.getTitle())
            descriptionEditText.setText(noteUi.getDescription())
        }
    }

    // todo check how to use independent if needed
    abstract class AbstractError(
        private val message: String,
        private val errorEnabled: Boolean
    ): NoteUiState() {
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText
        ) {
            titleLayout.isErrorEnabled = errorEnabled
            titleLayout.error = message
            descriptionLayout.isErrorEnabled = errorEnabled
            descriptionLayout.error = message
        }
    }

    data class ShowError(private val message: String): AbstractError(message, true)

    class ClearError : AbstractError("", false)
}
