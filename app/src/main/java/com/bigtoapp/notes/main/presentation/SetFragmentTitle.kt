package com.bigtoapp.notes.main.presentation

interface SetFragmentTitle {
    fun setFragmentTitle(): String
}

interface SetMutableTitles{
    fun setFragmentTitle(isInEdit: Boolean): String
}