package com.bigtoapp.notes.categories.presentation

import android.widget.TextView
import androidx.cardview.widget.CardView

class CategoryItemUi(
    private val head: TextView,
    private val view: CardView
): CategoryUi.Mapper<Unit> {

    override fun map(id: String, header: String, color: Int) {
        head.text = header
        view.setBackgroundColor(color)
        view.background.alpha = 85
    }
}