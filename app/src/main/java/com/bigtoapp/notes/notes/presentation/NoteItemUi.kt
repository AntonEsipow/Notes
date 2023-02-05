package com.bigtoapp.notes.notes.presentation

import android.widget.TextView
import com.bigtoapp.notes.main.presentation.Mapper
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

class NoteItemUi(
    private val head: TextView,
    private val body: TextView,
    private val date: TextView
): NoteUi.Mapper<Unit> {

    override fun map(id: String, header: String, description: String, performDate: String) {
        head.text = header
        body.text = description
        date.text = performDate
    }
}

class EditNoteUi(
    private val titleField: TextInputEditText,
    private val descriptionField: TextInputEditText,
    private val dateField: TextView
): NoteUi.Mapper<Unit> {

    override fun map(id: String, header: String, description: String, performDate: String) {
        titleField.setText(header)
        descriptionField.setText(description)
        dateField.text = performDate
    }

}