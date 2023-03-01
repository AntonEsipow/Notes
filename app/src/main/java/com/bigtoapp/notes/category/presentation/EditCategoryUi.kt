package com.bigtoapp.notes.category.presentation

import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.main.views.CustomTextInputEditText
import com.google.android.material.textfield.TextInputEditText

class EditCategoryUi(
    private val titleField: CustomTextInputEditText
): CategoryUi.Mapper<Unit> {

    override fun map(id: String, header: String, color: Int) {
        titleField.showText(header)
    }
}