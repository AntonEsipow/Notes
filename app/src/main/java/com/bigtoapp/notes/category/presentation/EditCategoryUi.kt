package com.bigtoapp.notes.category.presentation

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.category.color.ColorCommunication
import com.bigtoapp.notes.category.color.ColorCommunications
import com.bigtoapp.notes.category.color.ColorState
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.roundToInt

class EditCategoryUi(
    private val titleField: TextInputEditText
): CategoryUi.Mapper<Unit> {

    override fun map(id: String, header: String, color: Int) {
        titleField.setText(header)
    }
}