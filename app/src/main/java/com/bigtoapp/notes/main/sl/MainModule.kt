package com.bigtoapp.notes.main.sl

import com.bigtoapp.notes.main.presentation.MainViewModel

class MainModule(
    private val provideNavigation: ProvideNavigation
): Module<MainViewModel> {
    override fun viewModel(): MainViewModel = MainViewModel(provideNavigation.provideNavigation())
}