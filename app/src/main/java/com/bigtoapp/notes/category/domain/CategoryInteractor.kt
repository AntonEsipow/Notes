package com.bigtoapp.notes.category.domain

interface CategoryInteractor {

    suspend fun insertCategory(title: String)

    suspend fun updateCategory(categoryId: String, title: String)
}