package com.bigtoapp.notes.category.presentation

import android.widget.SeekBar
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.main.views.CustomTextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class CategoryUiState{

    abstract fun apply(
        titleLayout: TextInputLayout,
        titleEditText: CustomTextInputEditText,
        redBar: SeekBar,
        greenBar: SeekBar,
        blueBar: SeekBar
    )

    object AddCategory: CategoryUiState() {
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: CustomTextInputEditText,
            redBar: SeekBar,
            greenBar: SeekBar,
            blueBar: SeekBar
        ) {
            titleEditText.showText("")
            redBar.progress = 0
            greenBar.progress = 0
            blueBar.progress = 0
        }
    }

    data class EditCategory(
        private val categoryUi: CategoryUi
        ): CategoryUiState() {
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: CustomTextInputEditText,
            redBar: SeekBar,
            greenBar: SeekBar,
            blueBar: SeekBar
        ) {
            val mapper = EditCategoryUi(titleEditText)
            categoryUi.map(mapper)
        }
    }

    data class ShowError(
        private val message: String
    ) : CategoryUiState() {

        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: CustomTextInputEditText,
            redBar: SeekBar,
            greenBar: SeekBar,
            blueBar: SeekBar
        ) = with(titleEditText) {
            showError(message)
        }
    }

    object ClearError : CategoryUiState(){
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: CustomTextInputEditText,
            redBar: SeekBar,
            greenBar: SeekBar,
            blueBar: SeekBar
        ) {}
    }
}
