package com.bigtoapp.notes.categories.presentation

import android.widget.TextView

class CategoryItemUi(
    private val head: TextView
): CategoryUi.Mapper<Unit> {

    override fun map(id: String, header: String) {
        head.text = header
    }
}