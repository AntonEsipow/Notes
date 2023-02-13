package com.bigtoapp.notes.note.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.presentation.Communication
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.bigtoapp.notes.notes.presentation.NotesListCommunication

interface NoteCommunications: ObserveNote, GetList<NoteUi> {

    fun showState(uiState: NoteUiState)

    class Base(
        private val noteList: NotesListCommunication,
        private val state: NoteUiStateCommunication
    ): NoteCommunications{

        override fun showState(uiState: NoteUiState) = state.put(uiState)

        override fun observeState(owner: LifecycleOwner, observer: Observer<NoteUiState>) =
            state.observe(owner, observer)

        override fun getList(): List<NoteUi> = noteList.get()
    }
}

interface ObserveNote{
    fun observeState(owner: LifecycleOwner, observer: Observer<NoteUiState>)
}

interface NoteUiStateCommunication: Communication.Mutable<NoteUiState>{
    class Base: Communication.Post<NoteUiState>(), NoteUiStateCommunication
}

interface GetList<T>{
    fun getList(): List<T>
}