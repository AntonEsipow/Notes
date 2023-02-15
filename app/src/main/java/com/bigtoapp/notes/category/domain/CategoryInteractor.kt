package com.bigtoapp.notes.category.domain

import com.bigtoapp.notes.note.domain.Generate

interface CategoryInteractor {

    suspend fun insertCategory(title: String, color: Int)

    suspend fun updateCategory(categoryId: String, title: String, color: Int)

    class Base(
        private val repository: CategoryRepository,
        private val generator: Generate.GenerateId
    ): CategoryInteractor{

        override suspend fun insertCategory(title: String, color: Int) {
            val categoryId = generator.generateId()
            repository.insertCategory(categoryId, title, color)
        }

        override suspend fun updateCategory(categoryId: String, title: String, color: Int) =
            repository.updateCategory(categoryId, title, color)
    }
}