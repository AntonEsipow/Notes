package com.bigtoapp.notes.dialog.presentation

import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.main.presentation.Communication

interface SelectedCategoryCommunications {

    fun getSelectedCategory(): CategoryUi
    fun setSelectedCategory(category : CategoryUi)

    class Base(
        private val selectedCategory: SelectedCategory
    ): SelectedCategoryCommunications{

        override fun getSelectedCategory() =
            selectedCategory.emptyGet() ?: CategoryData.getDefaultCategory()

        override fun setSelectedCategory(category: CategoryUi) {
            selectedCategory.put(category)
        }
    }
}

interface SelectedCategory: Communication.Mutable<CategoryUi>{
    class Base: Communication.Ui<CategoryUi>(), SelectedCategory
}