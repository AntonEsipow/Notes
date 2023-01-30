package com.bigtoapp.notes.note.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.presentation.Communication
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.bigtoapp.notes.notes.presentation.NotesListCommunication

interface NoteCommunications: ObserveNote {

    fun getNotesList(): List<NoteUi>

    fun showState(uiState: NoteUiState)

    class Base(
        private val noteList: NotesListCommunication,
        private val state: NoteUiStateCommunication
    ): NoteCommunications{

        override fun getNotesList(): List<NoteUi> =
            noteList.get()

        override fun showState(uiState: NoteUiState) = state.put(uiState)

        override fun observeState(owner: LifecycleOwner, observer: Observer<NoteUiState>) =
            state.observe(owner, observer)
    }
}

interface ObserveNote{
    fun observeState(owner: LifecycleOwner, observer: Observer<NoteUiState>)
}

interface NoteUiStateCommunication: Communication.Mutable<NoteUiState>{
    class Base: Communication.Post<NoteUiState>(), NoteUiStateCommunication
}