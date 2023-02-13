package com.bigtoapp.notes.categories.domain

import com.bigtoapp.notes.notes.domain.NoteDomain

interface CategoriesRepository {

    suspend fun deleteCategory(categoryId: String)

    suspend fun allCategories(): List<CategoryDomain>
}