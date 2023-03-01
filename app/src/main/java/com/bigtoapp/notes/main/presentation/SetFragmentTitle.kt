package com.bigtoapp.notes.main.presentation

import androidx.annotation.StringRes

interface SetFragmentTitle {
    fun setFragmentTitle(@StringRes value: Int): String
}