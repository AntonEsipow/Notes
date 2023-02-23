package com.bigtoapp.notes.dialog.domain

import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.main.data.ListRepository

interface SelectCategoryInteractor {

    suspend fun allCategories(): List<CategoryDomain>

    class Base(
        private val repository: ListRepository<List<CategoryDomain>>
    ): SelectCategoryInteractor{

        override suspend fun allCategories() = repository.all()
    }
}