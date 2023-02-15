package com.bigtoapp.notes.categories.presentation

import android.graphics.Color
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlin.math.roundToInt

class CategoryItemUi(
    private val head: TextView,
    private val view: CardView
): CategoryUi.Mapper<Unit> {

    override fun map(id: String, header: String, color: Int) {
        head.text = header

        val categoryColor = Color.valueOf(color)
        val redValue = (categoryColor.red() * 255).roundToInt()
        val greenValue = (categoryColor.green() * 255).roundToInt()
        val blueValue = (categoryColor.blue() * 255).roundToInt()

        val back = Color.rgb(redValue, greenValue, blueValue)
        view.setBackgroundColor(back)
    }
}