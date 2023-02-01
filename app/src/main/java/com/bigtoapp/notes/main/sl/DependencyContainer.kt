package com.bigtoapp.notes.main.sl

import androidx.lifecycle.ViewModel
import com.bigtoapp.notes.main.presentation.MainViewModel
import com.bigtoapp.notes.note.presentation.NoteViewModel
import com.bigtoapp.notes.note.sl.NoteModule
import com.bigtoapp.notes.notes.presentation.NotesViewModel
import com.bigtoapp.notes.notes.sl.NotesModule
import java.lang.IllegalStateException

interface DependencyContainer {

    fun <T: ViewModel> module(clazz: Class<T>): Module<*>

    class Error: DependencyContainer{
        override fun <T : ViewModel> module(clazz: Class<T>): Module<*> =
            throw IllegalStateException("no module found for $clazz")
    }

    class Base(
        private val core: Core,
        private val dependencyContainer: DependencyContainer = Error()
    ): DependencyContainer {
        override fun <T : ViewModel> module(clazz: Class<T>): Module<*> = when(clazz){
            MainViewModel::class.java -> MainModule(core)
            NotesViewModel::class.java -> NotesModule(core)
            NoteViewModel::class.java -> NoteModule(core)
            else -> dependencyContainer.module(clazz)
        }
    }
}