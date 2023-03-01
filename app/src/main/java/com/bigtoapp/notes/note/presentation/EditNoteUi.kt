package com.bigtoapp.notes.note.presentation

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bigtoapp.notes.main.views.CustomTextInputEditText
import com.bigtoapp.notes.notes.presentation.NoteUi

class EditNoteUi(
    private val titleField: CustomTextInputEditText,
    private val descriptionField: CustomTextInputEditText,
    private val dateField: TextView,
    private val categoryText: TextView,
    private val noteLayout: ConstraintLayout
): NoteUi.Mapper<Unit> {

    override fun map(
        id: String,
        header: String,
        description: String,
        performDate: String,
        categoryId: String,
        categoryName: String,
        categoryColor: Int
    ) {
        titleField.showText(header)
        descriptionField.showText(description)
        dateField.text = performDate
        categoryText.text = categoryName

        noteLayout.setBackgroundColor(categoryColor)
        noteLayout.background.alpha = 40
    }
}