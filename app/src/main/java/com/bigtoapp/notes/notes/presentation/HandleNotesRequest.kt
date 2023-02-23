package com.bigtoapp.notes.notes.presentation

import android.view.View
import com.bigtoapp.notes.dialog.presentation.ShowScreenState
import com.bigtoapp.notes.main.communications.MutableShow
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.HandleRequest
import com.bigtoapp.notes.notes.domain.NoteDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HandleNotesRequest(
    private val dispatchers: DispatchersList,
    private val communications: MutableShow<NoteUi, NotesUiState>,
    private val mapper: NoteDomain.Mapper<NoteUi>,
    private val showState: ShowScreenState<List<NoteDomain>>
): HandleRequest<List<NoteDomain>> {

    override fun handle(coroutineScope: CoroutineScope, block: suspend () -> List<NoteDomain>) {
        communications.showProgress(View.VISIBLE)
        coroutineScope.launch(dispatchers.io()) {
            val list = block.invoke()
            communications.showProgress(View.GONE)
            showState.showState(list)
            communications.showList(list.map { it.map(mapper) })
        }
    }
}