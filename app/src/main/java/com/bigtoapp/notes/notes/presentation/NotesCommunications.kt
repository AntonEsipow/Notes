package com.bigtoapp.notes.notes.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.presentation.Communication

interface NotesCommunications: ObserveNotes, ShowNotesCommunications {

    fun showProgress(show: Int)

    class Base(
        private val progress: ProgressCommunication,
        private val state: NotesUiStateCommunication,
        private val notesList: NotesListCommunication
    ): NotesCommunications {

        override fun showProgress(show: Int) = progress.put(show)

        override fun showState(notesUiState: NotesUiState) = state.put(notesUiState)

        override fun showList(list: List<NoteUi>) = notesList.put(list)

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
            progress.observe(owner, observer)

        override fun observeState(owner: LifecycleOwner, observer: Observer<NotesUiState>) =
            state.observe(owner, observer)

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<NoteUi>>) =
            notesList.observe(owner, observer)
    }
}

interface ObserveNotes {

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>)

    fun observeState(owner: LifecycleOwner, observer: Observer<NotesUiState>)

    fun observeList(owner: LifecycleOwner, observer: Observer<List<NoteUi>>)
}

interface ShowNotesCommunications {

    fun showList(list: List<NoteUi>)
    fun showState(notesUiState: NotesUiState)

    class Base(
        private val notesList: NotesListCommunication,
        private val notesState: NotesUiStateCommunication
    ): ShowNotesCommunications {
        override fun showList(list: List<NoteUi>) = notesList.put(list)
        override fun showState(notesUiState: NotesUiState) = notesState.put(notesUiState)
    }
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