package com.bigtoapp.notes.main.presentation

import com.bigtoapp.notes.main.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest: BaseTest() {

    @Test
    fun `test navigation at start`() {
        val navigation = TestNavigationCommunication()
        val mainViewModel = MainViewModel(navigation)

        mainViewModel.singleInit(true)
        assertEquals(1, navigation.count)
        assertEquals(NavigationStrategy.Replace(Screen.Notes), navigation.strategy)

        mainViewModel.singleInit(false)
        assertEquals(1, navigation.count)
    }
}