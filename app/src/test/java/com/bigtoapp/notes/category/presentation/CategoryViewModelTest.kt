package com.bigtoapp.notes.category.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.category.color.ColorCommunications
import com.bigtoapp.notes.category.color.ColorState
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
    private lateinit var colorCommunications: TestColorCommunications
    private lateinit var manageResources: TestManageResources
    private lateinit var viewModel: CategoryViewModel
    private lateinit var navigation: TestNavigationCommunication

    @Before
    fun setUp(){
        navigation = TestNavigationCommunication()
        interactor = TestCategoryInteractor()
        communications = TestCategoryCommunications()
        manageResources = TestManageResources()
        colorCommunications = TestColorCommunications()
        viewModel = CategoryViewModel(
            manageResources,
            communications,
            colorCommunications,
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
        assertEquals(CategoryUiState.AddCategory(colorCommunications), communications.stateCalledList[0])
    }

    @Test
    fun `test display update category screen`() {
        communications.changeExpectedList(listOf(
            CategoryUi("1", "title", 123),
            CategoryUi("2", "shop", 123)
        ))

        viewModel.init(true, "2")
        assertEquals(
            CategoryUiState.EditCategory(CategoryUi("2", "shop", 123)),
            communications.stateCalledList[0]
        )
    }

    @Test
    fun `test no title error state`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        communications.changeExpectedList(listOf(
            CategoryUi("1", "title", 123),
            CategoryUi("2", "shop", 123)
        ))

        viewModel.init(true, "2")
        assertEquals(
            CategoryUiState.EditCategory(CategoryUi("2", "shop", 123)),
            communications.stateCalledList[0]
        )
        viewModel.saveCategory("","2", 123)
        assertEquals(CategoryUiState.ShowError("Enter something"), communications.stateCalledList[1])
    }

    @Test
    fun `test insert category`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")

        viewModel.init(true, "")
        assertEquals(CategoryUiState.AddCategory(colorCommunications), communications.stateCalledList[0])

        assertEquals(ColorState(0,0,0), colorCommunications.testColorState)
        viewModel.onRedChange(4)
        viewModel.onBlueChange(5)

        assertEquals(2, colorCommunications.colorStateCalledList.size)
        assertEquals(2, colorCommunications.getListCalledCount)
        assertEquals(ColorState(4,0,0), colorCommunications.colorStateCalledList[0])
        assertEquals(ColorState(0,0,5), colorCommunications.colorStateCalledList[1])

        viewModel.saveCategory("book", "", 123)

        assertEquals(1, interactor.insertCategoryCalledCount)
        assertEquals(0, interactor.updateCategoryCalledCount)

        assertEquals(
            CategoryUiState.AddCategory(colorCommunications),
            communications.stateCalledList[1]
        )
    }

    @Test
    fun `test update category`() = runBlocking {
        manageResources.makeExpectedAnswer("Enter something")
        communications.changeExpectedList(listOf(
            CategoryUi("1", "title", 123),
            CategoryUi("2", "shop", 123)
        ))

        viewModel.init(true, "2")
        assertEquals(
            CategoryUiState.EditCategory(CategoryUi("2", "shop", 123)),
            communications.stateCalledList[0]
        )

        colorCommunications.changeExpectedState(ColorState(1,2,3))
        viewModel.onRedChange(4)

        assertEquals(1, colorCommunications.colorStateCalledList.size)
        assertEquals(ColorState(4,2,3), colorCommunications.colorStateCalledList[0])
        assertEquals(1, colorCommunications.getListCalledCount)

        viewModel.saveCategory("book", "2", 123)
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

    override suspend fun insertCategory(title: String, color: Int){
        insertCategoryCalledCount++
    }

    override suspend fun updateCategory(categoryId: String, title: String, color: Int){
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

private class TestColorCommunications: ColorCommunications {

    var testColorState = ColorState()
    var getListCalledCount = 0
    val colorStateCalledList = mutableListOf<ColorState>()

    fun changeExpectedState(state: ColorState){
        testColorState = state
    }

    override fun showColor(state: ColorState) {
        colorStateCalledList.add(state)
    }

    override fun observeColor(owner: LifecycleOwner, observer: Observer<ColorState>) = Unit

    override fun emptyGet(): ColorState {
        getListCalledCount++
        return testColorState
    }

}

private class TestManageResources : ManageResources {
    private var string: String = ""

    fun makeExpectedAnswer(expected: String) {
        string = expected
    }

    override fun string(id: Int): String = string
}