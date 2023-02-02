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

    data class ShowErrorTitle(
        private val message: String
        ): NoteUiState() {
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText
        ) {
            titleLayout.isErrorEnabled = true
            titleLayout.error = message
        }
    }

    data class ShowErrorDescription(
        private val message: String
        ): NoteUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText
        ) {
            descriptionLayout.isErrorEnabled = true
            descriptionLayout.error = message
        }
    }

    class ClearError : NoteUiState() {
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText
        ) {
            titleLayout.isErrorEnabled = false
            titleLayout.error = ""
            descriptionLayout.isErrorEnabled = false
            descriptionLayout.error = ""
        }
    }
}
