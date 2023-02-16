package com.bigtoapp.notes.categories.data

import com.bigtoapp.notes.categories.domain.CategoryDomain
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class BaseCategoriesRepositoryTest {

    private lateinit var dao: TestCategoryDao
    private lateinit var repository: BaseCategoriesRepository

    @Before
    fun setUp(){
        dao = TestCategoryDao()
        repository = BaseCategoriesRepository(
            dao
        )
    }

    @Test
    fun `test all categories`(): Unit = runBlocking {
        dao.changeExpectedData(listOf(
            CategoryData("1", "title", 3),
            CategoryData("2", "book", 3)
        ))
        val actual = repository.all()
        val expected = listOf(
            CategoryDomain("1", "title", 3),
            CategoryDomain("2", "book", 3)
        )
        assertEquals(expected, actual)
        assertEquals(1, dao.allCategoriesCalledCount)
    }

    @Test
    fun `test insert category`(): Unit = runBlocking{
        dao.changeExpectedData(listOf(
            CategoryData("1", "title", 3),
            CategoryData("2", "book", 3)
        ))
        repository.insertCategory("3", "shop", 3)
        assertEquals(1, dao.insertCategoryCalledCount)

        val actual = repository.all()
        val expected = listOf(
            CategoryDomain("1", "title", 3),
            CategoryDomain("2", "book", 3),
            CategoryDomain("3", "shop", 3)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test update category`(): Unit = runBlocking{
        dao.changeExpectedData(listOf(
            CategoryData("1", "title", 3),
            CategoryData("2", "book", 3)
        ))
        repository.updateCategory("2", "shop", 5)
        assertEquals(1, dao.updateCategoryCalledCount)

        val actual = repository.all()
        val expected = listOf(
            CategoryDomain("1", "title", 3),
            CategoryDomain("2", "shop", 5)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test delete category`(): Unit = runBlocking{
        dao.changeExpectedData(listOf(
            CategoryData("1", "title", 3),
            CategoryData("2", "book", 3)
        ))
        repository.delete("1")
        assertEquals("1", dao.deleteCategoryCalledList[0])

        val actual = repository.all()
        val expected = listOf(
            CategoryDomain("2", "book", 3)
        )
        assertEquals(expected, actual)
    }
}

private class TestCategoryDao: CategoriesDao {

    var allCategoriesCalledCount = 0
    var updateCategoryCalledCount = 0
    var insertCategoryCalledCount = 0
    val deleteCategoryCalledList = mutableListOf<String>()

    val data = mutableListOf<CategoryData>()

    fun changeExpectedData(newData: List<CategoryData>): Unit = with(data) {
        clear()
        addAll(newData)
    }

    override fun insertCategory(categoryData: CategoryData) {
        insertCategoryCalledCount++
        data.add(categoryData)
    }

    override fun updateCategory(id: String, title: String, color: Int) {
        updateCategoryCalledCount++
        val item = data.find { it.mapId(id) }
        val index = data.indexOf(item)
        val categoryData = CategoryData(id, title, color)
        data[index] = categoryData
    }

    override fun allCategories(): List<CategoryData> {
        allCategoriesCalledCount++
        return data
    }

    override fun deleteCategory(categoryId: String) {
        deleteCategoryCalledList.add(categoryId)
        val item = data.find { it.mapId(categoryId) }
        data.remove(item)
    }
}