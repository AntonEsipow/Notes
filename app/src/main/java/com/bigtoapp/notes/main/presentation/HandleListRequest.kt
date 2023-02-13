package com.bigtoapp.notes.main.presentation

import com.bigtoapp.notes.notes.domain.NoteDomain
import kotlinx.coroutines.CoroutineScope

interface HandleRequest<T> {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> T
    )
}