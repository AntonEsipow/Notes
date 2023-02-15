package com.bigtoapp.notes.category.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.category.domain.CategoryInteractor
import com.bigtoapp.notes.main.CategoriesBaseTest
import com.bigtoapp.notes.main.presentation.HandleRequest
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.main.presentation.NavigationStrategy
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CategoryViewModelTest: CategoriesBaseTest() {

    private lateinit var interactor: TestCategoryInteractor
    private lateinit var communications: TestCategoryCommunications
    private lateinit var manageResources: TestManageResources
    private lateinit var viewModel: CategoryViewModel
    private lateinit var navigation: TestNavigationCommunication

    @Before
    fun setUp(){
        navigation = TestNavigationCommunication()
        interactor = TestCategoryInteractor()
        communications = TestCategoryCommunications()
        manageResources = TestManageResources()
        viewModel = CategoryViewModel(
            manageResources,
            communications,
            interactor,
            HandleRequest.Base(
                TestDispatcherList()
            ),
            navigation
        )
    }

    @Test
    fun `test display add category screen`() {

        viewModel.init(true, "")
        assertEquals(CategoryUiState.AddCategory, communications.stateCalledList[0])
    }

    @Test
    fun `test display update category screen`() {
        communications.changeExpectedList(listOf(
            CategoryUi("1", "title"),
            CategoryUi("2", "shop")
        ))

        viewModel.init(true, "2")
        assertEquals(
            CategoryUiState.EditCategory(CategoryUi("2", "shop")),
            communications.stateCalledList[0]
        )
    }

    @Test
    fun `test no title error state`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        communications.changeExpectedList(listOf(
            CategoryUi("1", "title"),
            CategoryUi("2", "shop")
        ))

        viewModel.init(true, "2")
        assertEquals(
            CategoryUiState.EditCategory(CategoryUi("2", "shop")),
            communications.stateCalledList[0]
        )
        viewModel.saveCategory("","2")
        assertEquals(CategoryUiState.ShowError("Enter something"), communications.stateCalledList[1])
    }

    @Test
    fun `test insert category`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")

        viewModel.init(true, "")
        assertEquals(CategoryUiState.AddCategory, communications.stateCalledList[0])

        viewModel.saveCategory("book", "")
        assertEquals(1, interactor.insertCategoryCalledCount)
        assertEquals(0, interactor.updateCategoryCalledCount)

        assertEquals(
            CategoryUiState.AddCategory,
            communications.stateCalledList[1]
        )
    }

    @Test
    fun `test update category`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        communications.changeExpectedList(listOf(
            CategoryUi("1", "title"),
            CategoryUi("2", "shop")
        ))

        viewModel.init(true, "2")
        assertEquals(
            CategoryUiState.EditCategory(CategoryUi("2", "shop")),
            communications.stateCalledList[0]
        )

        viewModel.saveCategory("book", "2")
        assertEquals(1, interactor.updateCategoryCalledCount)
        assertEquals(0, interactor.insertCategoryCalledCount)
        assertEquals("2", interactor.updateCategoryIdCheck[0])

        assertEquals(1, navigation.count)
        assertEquals(NavigationStrategy.Back, navigation.strategy)
    }

    @Test
    fun `test clear error`() {
        viewModel.clearError()

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is CategoryUiState.ClearError)
    }
}

private class TestCategoryInteractor: CategoryInteractor {

    var insertCategoryCalledCount = 0
    var updateCategoryCalledCount = 0
    val updateCategoryIdCheck = mutableListOf<String>()

    override suspend fun insertCategory(title: String){
        insertCategoryCalledCount++
    }

    override suspend fun updateCategory(categoryId: String, title: String){
        updateCategoryCalledCount++
        updateCategoryIdCheck.add(categoryId)
    }
}

private class TestCategoryCommunications: CategoryCommunications {

    val stateCalledList = mutableListOf<CategoryUiState>()
    var getCategoriesCalledCount = 0

    val listOfCategory = mutableListOf<CategoryUi>()

    fun changeExpectedList(list: List<CategoryUi>){
        listOfCategory.clear()
        listOfCategory.addAll(list)
    }

    override fun showState(uiState: CategoryUiState){
        stateCalledList.add(uiState)
    }

    override fun getList(): List<CategoryUi>{
        getCategoriesCalledCount++
        return listOfCategory
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<CategoryUiState>) = Unit
}

private class TestManageResources : ManageResources {
    private var string: String = ""

    fun makeExpectedAnswer(expected: String) {
        string = expected
    }

    override fun string(id: Int): String = string
}