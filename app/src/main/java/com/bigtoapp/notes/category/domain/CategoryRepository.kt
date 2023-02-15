package com.bigtoapp.notes.category.domain

interface CategoryRepository {

    suspend fun insertCategory(id: String, title: String, color: Int)

    suspend fun updateCategory(id: String, title: String, color: Int)
}