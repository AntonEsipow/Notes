package com.bigtoapp.notes.categories.domain

import com.bigtoapp.notes.main.data.ListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CategoriesInteractorTest {

    private lateinit var repository: TestCategoriesRepository
    private lateinit var interactor: CategoriesInteractor

    @Before
    fun setUp(){
        repository = TestCategoriesRepository()
        interactor = CategoriesInteractor(
            repository
        )
    }

    @Test
    fun `test all categories`() = runBlocking {
        repository.changeExpectedList(
            listOf(
                CategoryDomain(id = "1", title = "title", 3),
                CategoryDomain(id = "2", title = "book", 3)
            )
        )
        val actual = interactor.all()
        val expected = listOf(
            CategoryDomain(id = "1", title = "title", 3),
            CategoryDomain(id = "2", title = "book", 3)
        )
        assertEquals(expected, actual)
        assertEquals(1, repository.allCategoriesCalledCount)
    }

    @Test
    fun `test delete category`() = runBlocking {
        interactor.delete("1")
        assertEquals(1, repository.allCategoriesCalledCount)
        assertEquals("1", repository.deleteCategoryCalledList[0])
    }
}

private class TestCategoriesRepository: ListRepository<List<CategoryDomain>> {

    var allCategoriesCalledCount = 0
    val deleteCategoryCalledList = mutableListOf<String>()

    private val notesList = mutableListOf<CategoryDomain>()

    fun changeExpectedList(list: List<CategoryDomain>) = with(notesList){
        clear()
        addAll(list)
    }

    override suspend fun all(): List<CategoryDomain>{
        allCategoriesCalledCount++
        return notesList
    }

    override suspend fun delete(id: String) {
        deleteCategoryCalledList.add(id)
    }
}