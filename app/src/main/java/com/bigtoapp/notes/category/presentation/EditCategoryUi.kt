package com.bigtoapp.notes.category.presentation

import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.google.android.material.textfield.TextInputEditText

class EditCategoryUi(
    private val titleField: TextInputEditText
): CategoryUi.Mapper<Unit> {

    override fun map(id: String, header: String) {
        titleField.setText(header)
    }
}