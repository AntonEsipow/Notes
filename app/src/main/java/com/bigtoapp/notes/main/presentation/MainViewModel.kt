package com.bigtoapp.notes.main.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class MainViewModel(
    private val navigationCommunication: NavigationCommunication.Mutable
): ViewModel(), SingleInit, Communication.Observe<NavigationStrategy> {

    override fun singleInit(isFirstRun: Boolean) {
        if(isFirstRun){
            navigationCommunication.put(NavigationStrategy.Replace(Screen.Notes))
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<NavigationStrategy>) =
        navigationCommunication.observe(owner, observer)
}