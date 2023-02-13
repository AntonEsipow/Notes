package com.bigtoapp.notes.note.presentation

import android.widget.TextView
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class NoteUiState{

    abstract fun apply(
        titleLayout: TextInputLayout,
        titleEditText: TextInputEditText,
        descriptionLayout: TextInputLayout,
        descriptionEditText: TextInputEditText,
        dateText: TextView
    )

    object AddNote: NoteUiState(){

        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView
        ) {
            titleEditText.setText("")
            descriptionEditText.setText("")
            dateText.text = ""
        }
    }

    data class EditNote(private val noteUi: NoteUi): NoteUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView
        ) {
            val mapper = EditNoteUi(titleEditText, descriptionEditText, dateText)
            noteUi.map(mapper)
        }

    }

    data class ShowErrorTitle(
        private val message: String
        ): NoteUiState() {
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView
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
            descriptionEditText: TextInputEditText,
            dateText: TextView
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
            descriptionEditText: TextInputEditText,
            dateText: TextView
        ) {
            titleLayout.isErrorEnabled = false
            titleLayout.error = ""
            descriptionLayout.isErrorEnabled = false
            descriptionLayout.error = ""
        }
    }
}
