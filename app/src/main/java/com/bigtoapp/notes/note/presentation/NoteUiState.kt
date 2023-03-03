package com.bigtoapp.notes.note.presentation

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.main.views.CustomTextInputEditText
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.google.android.material.textfield.TextInputLayout

sealed class NoteUiState{

    abstract fun apply(
        titleLayout: TextInputLayout,
        titleEditText: CustomTextInputEditText,
        descriptionLayout: TextInputLayout,
        descriptionEditText: CustomTextInputEditText,
        dateText: TextView,
        categoryName: TextView,
        noteLayout: ConstraintLayout
    )

    data class AddNote(
        private val dateMessage: String,
        private val categoryMessage: String
    ): NoteUiState(){

        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: CustomTextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: ConstraintLayout
        ) {
            titleEditText.showText("")
            titleEditText.clearError()
            descriptionEditText.showText("")
            dateText.text = dateMessage
            categoryName.text = categoryMessage
            noteLayout.setBackgroundColor(CategoryData.DEFAULT_CATEGORY_COLOR)
        }
    }

    data class EditNote(private val noteUi: NoteUi): NoteUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: CustomTextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: ConstraintLayout
        ) {
            val mapper = EditNoteUi(titleEditText, descriptionEditText, dateText, categoryName, noteLayout)
            noteUi.map(mapper)
        }

    }

    data class EditDate(private val date: String): NoteUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: CustomTextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: ConstraintLayout
        ) {
            dateText.text = date
        }
    }

    data class EditCategory(
        private val category: String,
        private val color: Int
        ): NoteUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: CustomTextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: ConstraintLayout
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
            titleLayout: TextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: CustomTextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: ConstraintLayout
        ) {
            titleEditText.showError(message)
        }
    }

    object ClearError : NoteUiState() {
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: CustomTextInputEditText,
            descriptionLayout: TextInputLayout,
            descriptionEditText: CustomTextInputEditText,
            dateText: TextView,
            categoryName: TextView,
            noteLayout: ConstraintLayout
        ){
            titleEditText.clearError()
        }
    }
}
