package com.bigtoapp.notes.categories.presentation

import android.view.View
import com.bigtoapp.notes.categories.domain.CategoriesInteractor
import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.categories.domain.CategoryDomainToUi
import com.bigtoapp.notes.main.CategoriesBaseTest
import com.bigtoapp.notes.main.presentation.NavigationStrategy
import com.bigtoapp.notes.main.presentation.Screen
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NotesViewModelTest: CategoriesBaseTest() {

    private lateinit var interactor: TestCategoriesInteractor
    private lateinit var communications: TestCategoriesCommunications
    private lateinit var viewModel: CategoriesViewModel
    private lateinit var navigation: TestNavigationCommunication

    @Before
    fun setUp(){
        navigation = TestNavigationCommunication()
        communications = TestCategoriesCommunications()
        interactor = TestCategoriesInteractor()
        viewModel = CategoriesViewModel(
            interactor,
            communications,
            HandleCategoriesRequest(
                TestDispatcherList(),
                communications,
                CategoryDomainToUi()
            ),
            navigation
        )
    }

    @Test
    fun `test init no categories`() = runBlocking {
        interactor.changeExpectedList(emptyList())
        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(1, interactor.initCategoriesCalledCount)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(CategoriesUiState.NoCategories, communications.stateCalledList[0])

        assertEquals(0, communications.categoriesList.size)
        assertEquals(1, communications.timesShowList)
    }

    @Test
    fun `test init category`() = runBlocking {
        interactor.changeExpectedList(
            listOf(CategoryDomain("1", "title"))
        )
        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(1, interactor.initCategoriesCalledCount)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(CategoriesUiState.Categories, communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(1, communications.categoriesList.size)
        assertEquals(
            CategoryUi(id = "1", header="title"),
            communications.categoriesList[0]
        )

        viewModel.init(isFirstRun = false)
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(1, interactor.initCategoriesCalledCount)
        assertEquals(1, communications.timesShowList)
    }

    @Test
    fun `test init some categories`() = runBlocking {
        interactor.changeExpectedList(
            listOf(
                CategoryDomain("1", "title"),
                CategoryDomain("2", "shop")
            )
        )
        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(1, interactor.initCategoriesCalledCount)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(CategoriesUiState.Categories, communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(2, communications.categoriesList.size)
        assertEquals(
            CategoryUi("1", "title"),
            communications.categoriesList[0]
        )
        assertEquals(
            CategoryUi("2", "shop"),
            communications.categoriesList[1]
        )

        viewModel.init(isFirstRun = false)
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(1, interactor.initCategoriesCalledCount)
        assertEquals(1, communications.timesShowList)
    }

    @Test
    fun `test delete category`() = runBlocking {
        interactor.changeExpectedList(
            listOf(
                CategoryDomain("1", "title"),
                CategoryDomain("2", "shop")
            )
        )
        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(1, interactor.initCategoriesCalledCount)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(CategoriesUiState.Categories, communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(2, communications.categoriesList.size)
        assertEquals(
            CategoryUi("1", "title"),
            communications.categoriesList[0]
        )
        assertEquals(
            CategoryUi("2", "shop"),
            communications.categoriesList[1]
        )

        interactor.changeExpectedList(
            listOf(
                CategoryDomain("2", "shop")
            )
        )
        viewModel.deleteCategory("1")

        assertEquals(View.VISIBLE, communications.progressCalledList[2])
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[3])
        assertEquals(2, communications.stateCalledList.size)

        assertEquals(1, interactor.deleteCategoryCalledList.size)

        assertEquals(CategoriesUiState.Categories, communications.stateCalledList[1])
        assertEquals(
            CategoryUi("2","shop"),
            communications.categoriesList[0]
        )
    }

    @Test
    fun `test delete last category`() = runBlocking {
        interactor.changeExpectedList(
            listOf(
                CategoryDomain("1", "title")
            )
        )
        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(1, interactor.initCategoriesCalledCount)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(CategoriesUiState.Categories, communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(1, communications.categoriesList.size)
        assertEquals(
            CategoryUi("1", "title"),
            communications.categoriesList[0]
        )

        interactor.changeExpectedList(emptyList())
        viewModel.deleteCategory("1")

        assertEquals(1, interactor.deleteCategoryCalledList.size)

        assertEquals(View.VISIBLE, communications.progressCalledList[2])
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[3])
        assertEquals(2, communications.stateCalledList.size)

        assertEquals(CategoriesUiState.NoCategories, communications.stateCalledList[1])
        assertEquals(2, communications.timesShowList)
    }

    @Test
    fun `test navigation edit category`() {
        viewModel.editCategory("12345")
        assertEquals("12345", interactor.updatedCategoryId)

        assertEquals(1, navigation.count)
        assertEquals(NavigationStrategy.ReplaceToBackStack(Screen.Category), navigation.strategy)
    }

    @Test
    fun `test navigation add category`() {
        viewModel.addCategory()

        assertEquals(1, navigation.count)
        assertEquals(NavigationStrategy.ReplaceToBackStack(Screen.Category), navigation.strategy)
    }
}

private class TestCategoriesInteractor: CategoriesInteractor {

    var updatedCategoryId = ""

    var initCategoriesCalledCount = 0
    val editCategoryCalledList = mutableListOf<String>()
    val deleteCategoryCalledList = mutableListOf<String>()

    private var notesList = mutableListOf<CategoryDomain>()

    fun changeExpectedList(list: List<CategoryDomain>) {
        notesList.clear()
        notesList.addAll(list)
    }

    override suspend fun allCategories(): List<CategoryDomain> {
        initCategoriesCalledCount++
        return notesList
    }

    override suspend fun deleteCategory(categoryId: String): List<CategoryDomain>{
        deleteCategoryCalledList.add(categoryId)
        return notesList
    }

    override fun editCategory(categoryId: String) {
        updatedCategoryId = categoryId
        editCategoryCalledList.add(categoryId)
    }
}