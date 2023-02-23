package com.bigtoapp.notes.notes.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.communications.MutableObserve
import com.bigtoapp.notes.main.communications.MutableShow
import com.bigtoapp.notes.main.presentation.Communication

interface MutableNotesCommunications: MutableObserve<NoteUi, NotesUiState>, MutableShow<NoteUi, NotesUiState>

class NotesCommunications(
        private val progress: ProgressCommunication,
        private val state: NotesUiStateCommunication,
        private val notesList: NotesListCommunication
): MutableNotesCommunications {

    override fun showProgress(show: Int) = progress.put(show)

    override fun showState(showState: NotesUiState) = state.put(showState)

    override fun showList(showList: List<NoteUi>) = notesList.put(showList)

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        progress.observe(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<NotesUiState>) =
        state.observe(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NoteUi>>) =
        notesList.observe(owner, observer)
}


interface ProgressCommunication: Communication.Mutable<Int>{
    class Base: Communication.Post<Int>(), ProgressCommunication
}

interface NotesUiStateCommunication: Communication.Mutable<NotesUiState>{
    class Base: Communication.Post<NotesUiState>(), NotesUiStateCommunication
}

interface NotesListCommunication: Communication.Mutable<List<NoteUi>>{
    class Base: Communication.Post<List<NoteUi>>(), NotesListCommunication
}