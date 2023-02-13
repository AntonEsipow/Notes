package com.bigtoapp.notes.main.presentation

import com.bigtoapp.notes.notes.domain.NoteDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface HandleRequest<T> {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> T
    )

    class Base(
        private val dispatchers: DispatchersList
    ):HandleRequest<Unit> {
        override fun handle(coroutineScope: CoroutineScope, block: suspend () -> Unit) {
            coroutineScope.launch(dispatchers.io()) {
                block.invoke()
            }
        }
    }
}