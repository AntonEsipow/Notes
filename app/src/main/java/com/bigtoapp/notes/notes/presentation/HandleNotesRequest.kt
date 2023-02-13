package com.bigtoapp.notes.notes.presentation

import android.view.View
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.HandleRequest
import com.bigtoapp.notes.notes.domain.NoteDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HandleNotesRequest(
    private val dispatchers: DispatchersList,
    private val communications: NotesCommunications,
    private val mapper: NoteDomain.Mapper<NoteUi>
): HandleRequest<List<NoteDomain>> {

    override fun handle(coroutineScope: CoroutineScope, block: suspend () -> List<NoteDomain>) {
        communications.showProgress(View.VISIBLE)
        coroutineScope.launch(dispatchers.io()) {
            val list = block.invoke()
            communications.showProgress(View.GONE)
            // todo move out of here
            communications.showState(
                if(list.isEmpty())
                    NotesUiState.NoNotes
                else
                    NotesUiState.Notes
            )
            communications.showList(list.map { it.map(mapper) })
        }
    }
}