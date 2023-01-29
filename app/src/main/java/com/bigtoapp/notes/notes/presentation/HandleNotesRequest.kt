package com.bigtoapp.notes.notes.presentation

import android.view.View
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.notes.domain.NoteDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface HandleNotesRequest {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> List<NoteDomain>
    )

    class Base(
        private val dispatchers: DispatchersList,
        private val communications: NotesCommunications,
        private val mapper: NoteDomain.Mapper<NoteUi>
    ): HandleNotesRequest {

        override fun handle(coroutineScope: CoroutineScope, block: suspend () -> List<NoteDomain>) {
            communications.showProgress(View.VISIBLE)
            coroutineScope.launch(dispatchers.io()) {
                val list = block.invoke()
                communications.showProgress(View.GONE)
                // todo move out of here
                communications.showState(
                    if(list.isEmpty())
                        NotesUiState.NoNotes
                    else {
                        communications.showList(list.map { it.map(mapper) })
                        NotesUiState.Notes
                    }
                )
            }
        }
    }
}