package com.bigtoapp.notes.category.domain

import com.bigtoapp.notes.note.domain.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CategoryInteractorTest {

    private lateinit var interactor: CategoryInteractor
    private lateinit var repository: TestCategoryRepository
    private lateinit var generator: TestCategoryGenerator

    @Before
    fun setUp(){
        generator = TestCategoryGenerator()
        repository = TestCategoryRepository()
        interactor = CategoryInteractor.Base(
            repository,
            generator
        )
    }

    @Test
    fun `test insert categories`() = runBlocking {
        interactor.insertCategory("shop", 2)
        Assert.assertEquals(1, repository.insertCategoryCalledCount)
        Assert.assertEquals(0, repository.updateCategoryCalledCount)
        Assert.assertEquals("1", repository.idCalledList[0])

        interactor.insertCategory("watch", 3)
        Assert.assertEquals(2, repository.insertCategoryCalledCount)
        Assert.assertEquals(0, repository.updateCategoryCalledCount)
        Assert.assertEquals("2", repository.idCalledList[1])
    }

    @Test
    fun `test update category`() = runBlocking {
        interactor.updateCategory("1","shop", 2)
        Assert.assertEquals(1, repository.updateCategoryCalledCount)
        Assert.assertEquals(0, repository.insertCategoryCalledCount)
    }
}

private class TestCategoryRepository: CategoryRepository {

    var insertCategoryCalledCount = 0
    var updateCategoryCalledCount = 0

    val idCalledList = mutableListOf<String>()

    override suspend fun insertCategory(id: String, title: String, color: Int) {
        insertCategoryCalledCount++
        idCalledList.add(id)
    }

    override suspend fun updateCategory(id: String, title: String, color: Int) {
        updateCategoryCalledCount++
    }
}

private class TestCategoryGenerator: Generate.GenerateId {

    private var id = 0
    private var time = 0L

    override fun generateId(): String {
        ++id
        return id.toString()
    }
}