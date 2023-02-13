package com.bigtoapp.notes.categories.domain

import com.bigtoapp.notes.category.data.CategoryEditOptions
import com.bigtoapp.notes.note.data.NoteEditOptions
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.domain.NotesInteractor
import com.bigtoapp.notes.notes.domain.NotesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CategoriesInteractorTest {

    private lateinit var repository: TestCategoriesRepository
    private lateinit var interactor: CategoriesInteractor

    @Before
    fun setUp(){
        repository = TestCategoriesRepository()
        interactor = CategoriesInteractor(
            repository,
            CategoryEditOptions()
        )
    }

    @Test
    fun `test all categories`() = runBlocking {
        repository.changeExpectedList(
            listOf(
                CategoryDomain(id = "1", title = "title"),
                CategoryDomain(id = "2", title = "book")
            )
        )
        val actual = interactor.all()
        val expected = listOf(
            CategoryDomain(id = "1", title = "title"),
            CategoryDomain(id = "2", title = "book")
        )
        Assert.assertEquals(expected, actual)
        Assert.assertEquals(1, repository.allCategoriesCalledCount)
    }

    @Test
    fun `test delete category`() = runBlocking {
        interactor.delete("1")
        Assert.assertEquals(1, repository.allCategoriesCalledCount)
        Assert.assertEquals("1", repository.deleteCategoryCalledList[0])
    }
}

private class TestCategoriesRepository: CategoriesRepository {

    var allCategoriesCalledCount = 0
    val deleteCategoryCalledList = mutableListOf<String>()

    private val notesList = mutableListOf<CategoryDomain>()

    fun changeExpectedList(list: List<CategoryDomain>) = with(notesList){
        clear()
        addAll(list)
    }

    override suspend fun allCategories(): List<CategoryDomain>{
        allCategoriesCalledCount++
        return notesList
    }

    override suspend fun deleteCategory(categoryId: String) {
        deleteCategoryCalledList.add(categoryId)
    }
}