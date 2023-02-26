package com.bigtoapp.notes.dialog.presentation

import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bigtoapp.notes.categories.presentation.CategoryUi

class SelectCategoryItemUi(
    private val head: TextView,
    private val view: CardView
): SelectedCategoryUi.Mapper<Unit> {

    override fun map(id: String, header: String, color: Int, selected: Boolean) {
        head.text = header
        view.setBackgroundColor(color)
        if(!selected)
            view.background.alpha = 85
    }
}