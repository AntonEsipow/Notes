package com.bigtoapp.notes.note.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.communications.ObserveState
import com.bigtoapp.notes.main.communications.ShowState
import com.bigtoapp.notes.main.presentation.Communication
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.bigtoapp.notes.notes.presentation.NotesListCommunication

interface MutableNoteCommunication: ObserveState<NoteUiState>,
    ShowState<NoteUiState>, GetList<NoteUi>

class NoteStateCommunication(
    private val state: NoteUiStateCommunication,
    private val noteList: NotesListCommunication
): MutableNoteCommunication{
    override fun showState(showState: NoteUiState) = state.put(showState)

    override fun observeState(owner: LifecycleOwner, observer: Observer<NoteUiState>) =
        state.observe(owner, observer)

    override fun getList(): List<NoteUi> = noteList.get()
}

interface NoteUiStateCommunication: Communication.Mutable<NoteUiState>{
    class Base: Communication.Post<NoteUiState>(), NoteUiStateCommunication
}

interface GetList<T>{
    fun getList(): List<T>
}