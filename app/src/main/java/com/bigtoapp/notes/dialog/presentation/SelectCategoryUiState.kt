package com.bigtoapp.notes.dialog.presentation

import android.widget.TextView

sealed class SelectCategoryUiState {

    abstract fun apply(titleText: TextView)

    object DefaultCategory: SelectCategoryUiState(){
        override fun apply(titleText: TextView) {
            // todo refactor
            titleText.text = "Default"
        }
    }

    object Categories: SelectCategoryUiState(){
        override fun apply(titleText: TextView) {
            // todo refactor
            titleText.text = "Categories"
        }
    }
}