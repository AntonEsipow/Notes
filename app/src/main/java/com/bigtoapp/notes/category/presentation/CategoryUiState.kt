package com.bigtoapp.notes.category.presentation

import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class CategoryUiState{

    abstract fun apply(
        titleLayout: TextInputLayout,
        titleEditText: TextInputEditText
    )

    object AddCategory: CategoryUiState() {
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText
        ) {
            titleEditText.setText("")
        }
    }

    data class EditCategory(private val categoryUi: CategoryUi): CategoryUiState() {
        override fun apply(
            titleLayout: TextInputLayout,
            titleEditText: TextInputEditText
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
            titleEditText: TextInputEditText
        ) = with(titleLayout) {
            isErrorEnabled = errorEnabled
            error = message
        }
    }

    data class ShowError(private val text: String) : AbstractError(text, true)
    class ClearError : AbstractError("", false)
}
