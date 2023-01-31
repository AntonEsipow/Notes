package com.bigtoapp.notes.note.presentation

import android.view.View
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.presentation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface HandleNoteRequest {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> List<NoteDomain>
    )

    class Base(
        private val dispatchers: DispatchersList,
        private val communications: ShowListCommunication,
        private val mapper: NoteDomain.Mapper<NoteUi>
    ): HandleNoteRequest {
        // todo check loading state
        override fun handle(coroutineScope: CoroutineScope, block: suspend () -> List<NoteDomain>) {
            coroutineScope.launch(dispatchers.io()) {
                val list = block.invoke()
                communications.showList(list.map { it.map(mapper) })
            }
        }
    }
}