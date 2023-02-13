package com.bigtoapp.notes.categories.presentation

import android.view.View
import android.widget.TextView
import com.bigtoapp.notes.notes.presentation.NotesUiState

sealed class CategoriesUiState {

    abstract fun apply(textView: TextView)

    object NoCategories: CategoriesUiState(){
        override fun apply(textView: TextView) {
            textView.visibility = View.VISIBLE
        }
    }

    object Categories: CategoriesUiState(){
        override fun apply(textView: TextView) {
            textView.visibility = View.GONE
        }
    }
}