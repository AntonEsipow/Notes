package com.bigtoapp.notes.dialog.presentation

import android.widget.TextView

sealed class SelectCategoryUiState {

    abstract fun apply(titleText: TextView)

    abstract class Abstract(
        private val message: String
    ): SelectCategoryUiState(){
        override fun apply(titleText: TextView) {
            titleText.text = message
        }
    }

    data class DefaultCategory(private val message: String): Abstract(message)

    data class Categories(private val message: String): Abstract(message)

    data class SelectedCategory(private val category: SelectedCategoryUi): SelectCategoryUiState(){
        override fun apply(titleText: TextView) {
            titleText.text = category.map(MapSelectedCategoryName())
        }
    }
}