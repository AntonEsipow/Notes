package com.bigtoapp.notes.note.presentation

import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.HandleRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HandleNoteRequest(
    private val dispatchers: DispatchersList
): HandleRequest<Unit> {

    override fun handle(coroutineScope: CoroutineScope, block: suspend () -> Unit) {
        coroutineScope.launch(dispatchers.io()) {
            block.invoke()
        }
    }
}