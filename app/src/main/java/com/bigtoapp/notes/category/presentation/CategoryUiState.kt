package com.bigtoapp.notes.category.presentation

import android.widget.SeekBar
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class CategoryUiState{

    abstract fun apply(
        titleLayout: TextInputLayout,
        titleEditText: TextInputEditText,
        redBar: SeekBar,
        greenBar: SeekBar,
        blueBar: SeekBar
    )

    object AddCategory: CategoryUiState() {
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            redBar: SeekBar,
            greenBar: SeekBar,
            blueBar: SeekBar
        ) {
            titleEditText.setText("")
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
            titleEditText: TextInputEditText,
            redBar: SeekBar,
            greenBar: SeekBar,
            blueBar: SeekBar
        ) {
            val mapper = EditCategoryUi(titleEditText)
            categoryUi.map(mapper)
        }
    }

    abstract class AbstractError(
        private val message: String,
        private val errorEnabled: Boolean
    ) : CategoryUiState() {

        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText,
            redBar: SeekBar,
            greenBar: SeekBar,
            blueBar: SeekBar
        ) = with(titleLayout) {
            isErrorEnabled = errorEnabled
            error = message
        }
    }

    data class ShowError(private val text: String) : AbstractError(text, true)
    class ClearError : AbstractError("", false)
}
