package com.bigtoapp.notes.categories.data

import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.category.domain.CategoryRepository
import com.bigtoapp.notes.main.data.ListRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BaseCategoriesRepository(
    private val dao: CategoriesDao
): ListRepository<List<CategoryDomain>>, CategoryRepository {

    private val mutex = Mutex()

    override suspend fun insertCategory(id: String, title: String, color: Int) = mutex.withLock {
        val data = CategoryData(id, title, color)
        dao.insertCategory(data)
    }

    override suspend fun updateCategory(id: String, title: String, color: Int) = mutex.withLock {
        dao.updateCategory(id, title, color)
    }

    override suspend fun delete(id: String) = mutex.withLock {
        dao.deleteCategory(id)
    }

    override suspend fun all(): List<CategoryDomain> = mutex.withLock {
        val data = dao.allCategories()
        return data.map { CategoryDomain(it.id, it.title, it.color) }
    }
}