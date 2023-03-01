package com.bigtoapp.notes.main.views

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

interface CustomTextInputEditText {
    fun showText(text: String)
    fun showError(errorMessage: String)
}

class BaseCustomTextInputEditText : TextInputEditText, CustomTextInputEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun showText(text: String) {
        setText(text)
    }

    override fun showError(errorMessage: String) {
        error = errorMessage
    }
}