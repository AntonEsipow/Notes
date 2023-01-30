package com.bigtoapp.notes.note.presentation

import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.presentation.HandleNotesRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface HandleNoteRequest {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> Unit
    )

    class Base(
        private val dispatchers: DispatchersList
    ): HandleNoteRequest{
        override fun handle(coroutineScope: CoroutineScope, block: suspend () -> Unit) {
            coroutineScope.launch(dispatchers.io()) { block.invoke() }
        }
    }
}