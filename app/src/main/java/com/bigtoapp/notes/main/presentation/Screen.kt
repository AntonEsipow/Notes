package com.bigtoapp.notes.main.presentation

import com.bigtoapp.notes.category.presentation.CategoryFragment
import com.bigtoapp.notes.note.presentation.NoteFragment
import com.bigtoapp.notes.notes.presentation.NotesFragment

sealed class Screen {

    abstract fun fragment(): Class<out BaseFragment<*>>

    object Notes: Screen(){
        override fun fragment(): Class<out BaseFragment<*>> = NotesFragment::class.java
    }

    object Note: Screen(){
        override fun fragment(): Class<out BaseFragment<*>> = NoteFragment::class.java
    }

    object Category: Screen(){
        override fun fragment(): Class<out BaseFragment<*>> = CategoryFragment::class.java
    }
}