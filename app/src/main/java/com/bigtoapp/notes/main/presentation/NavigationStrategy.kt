package com.bigtoapp.notes.main.presentation

import androidx.core.content.contentValuesOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

interface NavigationStrategy {

    fun navigate(fragmentManager: FragmentManager, containerId: Int)

    abstract class Abstract(
        protected open val screen: Screen
    ): NavigationStrategy {

        override fun navigate(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.beginTransaction()
                .executeTransaction(containerId)
                .commit()
        }

        protected abstract fun FragmentTransaction
                .executeTransaction(containerId: Int): FragmentTransaction
    }

    data class Replace(override val screen: Screen): Abstract(screen) {
        override fun FragmentTransaction.executeTransaction(containerId: Int) =
            replace(containerId,screen.fragment().newInstance())
    }

    data class Add(override val screen: Screen): Abstract(screen) {
        override fun FragmentTransaction.executeTransaction(containerId: Int) =
            screen.fragment().let {
                add(containerId, it.newInstance()).addToBackStack(it.simpleName)
            }
    }
}