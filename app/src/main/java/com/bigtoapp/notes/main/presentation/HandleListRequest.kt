package com.bigtoapp.notes.main.presentation

import com.bigtoapp.notes.notes.domain.NoteDomain
import kotlinx.coroutines.CoroutineScope

interface HandleListRequest<T> {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> List<T>
    )
}