package com.bigtoapp.notes.notes.presentation

import android.view.View
import android.widget.TextView

sealed class NotesUiState {

    abstract fun apply(textView: TextView)

    object NoNotes: NotesUiState(){
        override fun apply(textView: TextView) {
            textView.visibility = View.GONE
        }
    }

    object Notes: NotesUiState(){
        override fun apply(textView: TextView) {
            textView.visibility = View.GONE
        }
    }
}