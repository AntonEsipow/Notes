package com.bigtoapp.notes.categories.domain

import com.bigtoapp.notes.notes.domain.NoteDomain

interface CategoriesInteractor {

    suspend fun allCategories(): List<CategoryDomain>

    suspend fun deleteCategory(categoryId: String): List<CategoryDomain>

    fun editCategory(categoryId: String)
}