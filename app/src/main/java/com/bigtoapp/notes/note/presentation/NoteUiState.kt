package com.bigtoapp.notes.note.presentation

import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import com.bigtoapp.notes.main.views.CustomTextInputEditText
import com.bigtoapp.notes.main.views.CustomTextInputLayout
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class NoteUiState{

    abstract fun apply(
        titleLayout: CustomTextInputLayout,
        titleEditText: CustomTextInputEditText,
        descriptionLayout: TextInputLayout,
        descriptionEditText: TextInputEditText,
        dateText: TextView,
        categoryName: TextView,
        noteLayout: LinearLayout
    )

    object AddNote: NoteUiState(){
        override fun apply(
            titleLayout: CustomTextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: LinearLayout
        ) {
            titleEditText.showText("")
            descriptionEditText.setText("")
            dateText.text = ""
            categoryName.text = ""
            noteLayout.setBackgroundColor(Color.WHITE)
        }
    }

    data class EditNote(private val noteUi: NoteUi): NoteUiState(){
        override fun apply(
            titleLayout: CustomTextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: LinearLayout
        ) {
            val mapper = EditNoteUi(titleEditText, descriptionEditText, dateText, categoryName, noteLayout)
            noteUi.map(mapper)
        }

    }

    data class EditDate(private val date: String): NoteUiState(){
        override fun apply(
            titleLayout: CustomTextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: LinearLayout
        ) {
            dateText.text = date
        }
    }

    data class EditCategory(
        private val category: String,
        private val color: Int
        ): NoteUiState(){
        override fun apply(
            titleLayout: CustomTextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: LinearLayout
        ) {
            categoryName.text = category
            noteLayout.setBackgroundColor(color)
            noteLayout.background.alpha = 40
        }
    }

    // todo think abstract
    data class ShowErrorTitle(
        private val message: String
        ): NoteUiState() {
        override fun apply(
            titleLayout: CustomTextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: LinearLayout
        ) = with(titleLayout){
            changeErrorEnabled(true)
            showError(message)
        }
    }

    data class ShowErrorDescription(
        private val message: String
        ): NoteUiState(){
        override fun apply(
            titleLayout: CustomTextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: LinearLayout
        ) = with(descriptionLayout){
            isErrorEnabled = true
            error = message
        }
    }

    object ClearError : NoteUiState() {
        override fun apply(
            titleLayout: CustomTextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: TextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: LinearLayout
        ) {
            titleLayout.changeErrorEnabled(false)
            titleLayout.showError("")
            descriptionLayout.isErrorEnabled = false
            descriptionLayout.error = ""
        }
    }
}
