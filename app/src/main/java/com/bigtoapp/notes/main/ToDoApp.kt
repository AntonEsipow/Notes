package com.bigtoapp.notes.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bigtoapp.notes.BuildConfig
import com.bigtoapp.notes.main.sl.*

class ToDoApp: Application(), ProvideViewModel {

    private lateinit var viewModelsFactory: ViewModelsFactory

    override fun onCreate() {
        super.onCreate()
        val provideInstances = if(BuildConfig.DEBUG)
            ProvideInstances.Mock(this)
        else
            ProvideInstances.Release(this)
        viewModelsFactory = ViewModelsFactory(
            DependencyContainer.Base(Core.Base(this, provideInstances))
        )
    }
    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T =
        ViewModelProvider(owner, viewModelsFactory)[clazz]
}