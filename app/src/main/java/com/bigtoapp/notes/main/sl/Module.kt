package com.bigtoapp.notes.main.sl

import androidx.lifecycle.ViewModel

interface Module<T: ViewModel> {
    fun viewModel(): T
}