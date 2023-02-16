package com.bigtoapp.notes.note.presentation

import android.widget.TextView
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.google.android.material.textfield.TextInputEditText

class EditNoteUi(
    private val titleField: TextInputEditText,
    private val descriptionField: TextInputEditText,
    private val dateField: TextView
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
        titleField.setText(header)
        descriptionField.setText(description)
        dateField.text = performDate
    }
}