package com.bigtoapp.notes.notes.presentation

import android.graphics.Color
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.Mapper
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

class NoteItemUi(
    private val head: TextView,
    private val body: TextView,
    private val date: TextView,
    private val category: TextView,
    private val card: CardView
): NoteUi.Mapper<Unit> {

    override fun map(id: String, header: String, description: String, performDate: String,
                     categoryId: String, categoryName: String, categoryColor: Int) {
        head.text = header
        body.text = description
        date.text = performDate
        category.text = categoryName
        card.setBackgroundColor(categoryColor)
        card.background.alpha = 85
    }
}