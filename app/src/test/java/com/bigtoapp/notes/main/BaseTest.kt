package com.bigtoapp.notes.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.NavigationCommunication
import com.bigtoapp.notes.main.presentation.NavigationStrategy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

abstract class BaseTest {

    protected class TestNavigationCommunication : NavigationCommunication.Mutable {

        lateinit var strategy: NavigationStrategy
        var count = 0
        override fun observe(owner: LifecycleOwner, observer: Observer<NavigationStrategy>) =Unit

        override fun put(value: NavigationStrategy) {
            strategy = value
            count++
        }
    }

    protected class TestDispatcherList(
        private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
    ): DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }
}