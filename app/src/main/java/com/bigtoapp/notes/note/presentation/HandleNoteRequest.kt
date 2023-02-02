package com.bigtoapp.notes.note.presentation

import android.view.View
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.HandleListRequest
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.presentation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HandleNoteRequest(
    private val dispatchers: DispatchersList,
    private val communications: ShowNotesCommunications,
    private val mapper: NoteDomain.Mapper<NoteUi>
): HandleListRequest<NoteDomain> {
    // todo check loading state
    override fun handle(coroutineScope: CoroutineScope, block: suspend () -> List<NoteDomain>) {
        coroutineScope.launch(dispatchers.io()) {
            val list = block.invoke()
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