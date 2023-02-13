package com.bigtoapp.notes.category.domain

import com.bigtoapp.notes.note.domain.Generate

interface CategoryInteractor {

    suspend fun insertCategory(title: String)

    suspend fun updateCategory(categoryId: String, title: String)

    class Base(
        private val repository: CategoryRepository,
        private val generator: Generate.GenerateId
    ): CategoryInteractor{

        override suspend fun insertCategory(title: String) {
            val categoryId = generator.generateId()
            repository.insertCategory(categoryId, title)
        }

        override suspend fun updateCategory(categoryId: String, title: String) =
            repository.updateCategory(categoryId, title)
    }
}