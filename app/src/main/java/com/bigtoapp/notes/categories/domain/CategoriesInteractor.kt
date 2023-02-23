package com.bigtoapp.notes.categories.domain

import com.bigtoapp.notes.main.data.MutableListRepository
import com.bigtoapp.notes.main.domain.ListInteractor

class CategoriesInteractor(
    private val repository: MutableListRepository<List<CategoryDomain>>
):ListInteractor<List<CategoryDomain>> {

    override suspend fun all() = repository.all()

    override suspend fun delete(id: String): List<CategoryDomain> {
        repository.delete(id)
        return repository.all()
    }
}