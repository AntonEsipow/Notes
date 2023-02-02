package com.bigtoapp.notes.notes.presentation

import android.widget.TextView
import com.bigtoapp.notes.main.presentation.Mapper
import org.w3c.dom.Text

class NoteItemUi(
    private val head: TextView,
    private val body: TextView
): NoteUi.Mapper<Unit> {

    override fun map(id: String, header: String, description: String) {
        head.text = header
        body.text = description
    }
}