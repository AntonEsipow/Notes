package com.bigtoapp.notes.dialog.presentation

import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.main.presentation.Communication

interface SelectedCategoryCommunications {

    fun getSelectedCategory(): SelectedCategoryUi
    fun setSelectedCategory(category : SelectedCategoryUi)

    class Base(
        private val selectedCategory: SelectedCategory
    ): SelectedCategoryCommunications{

        override fun getSelectedCategory() =
            selectedCategory.emptyGet() ?: CategoryData.getDefaultCategory()

        override fun setSelectedCategory(category: SelectedCategoryUi) {
            selectedCategory.put(category)
        }
    }
}

interface SelectedCategory: Communication.Mutable<SelectedCategoryUi>{
    class Base: Communication.Ui<SelectedCategoryUi>(), SelectedCategory
}