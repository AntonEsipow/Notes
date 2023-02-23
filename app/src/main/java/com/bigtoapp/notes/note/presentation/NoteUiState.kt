package com.bigtoapp.notes.note.presentation

import android.widget.TextView
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

// todo refactor make better
// todo make some apply for each case dates, errors, input fields
// todo and abstract class
sealed class NoteUiState{

    abstract fun apply(
        titleLayout: TextInputLayout,
        titleEditText: TextInputEditText,
        descriptionLayout: TextInputLayout,
        descriptionEditText: TextInputEditText,
        dateText: TextView,
        categoryName: TextView
    )

    object AddNote: NoteUiState(){

        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView
        ) {
            titleEditText.setText("")
            descriptionEditText.setText("")
            dateText.text = ""
            categoryName.text = ""
        }
    }

    data class EditNote(private val noteUi: NoteUi): NoteUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView
        ) {
            val mapper = EditNoteUi(titleEditText, descriptionEditText, dateText, categoryName)
            noteUi.map(mapper)
        }

    }

    data class EditDate(private val date: String): NoteUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView
        ) {
            dateText.text = date
        }
    }

    data class EditCategory(private val category: String): NoteUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView
        ) {
            categoryName.text = category
        }
    }

    // todo make abstract
    data class ShowErrorTitle(
        private val message: String
        ): NoteUiState() {
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView
        ) {
            titleLayout.isErrorEnabled = true
            titleLayout.error = message
        }
    }

    // todo make abstract
    data class ShowErrorDescription(
        private val message: String
        ): NoteUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView
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
            dateText: TextView,
            categoryName: TextView
        ) {
            titleLayout.isErrorEnabled = false
            titleLayout.error = ""
            descriptionLayout.isErrorEnabled = false
            descriptionLayout.error = ""
        }
    }
}
