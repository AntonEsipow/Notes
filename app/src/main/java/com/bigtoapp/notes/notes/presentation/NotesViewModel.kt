package com.bigtoapp.notes.notes.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.main.presentation.Init
import com.bigtoapp.notes.notes.domain.NotesInteractor

class NotesViewModel(
    private val interactor: NotesInteractor,
    private val communications: NotesCommunications,
    private val handleRequest: HandleNotesRequest
): ViewModel(), Init, ObserveNotes, NotesScreenOperations {

    override fun init(isFirstRun: Boolean) {
        if(isFirstRun)
            handleRequest.handle(viewModelScope){
                interactor.allNotes()
            }
    }

    // todo add when navigation strategy
    override fun addNote() = Unit

    override fun deleteNote(noteId: String) = handleRequest.handle(viewModelScope){
        interactor.deleteNote(noteId)
    }

    override fun editNote(noteId: String) = interactor.editNote(noteId)

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        communications.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<NotesUiState>) =
        communications.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NoteUi>>) =
        communications.observeList(owner, observer)
}

interface NotesScreenOperations: NoteOperations{
    fun addNote()
}

interface NoteOperations{
    fun deleteNote(noteId: String)
    fun editNote(noteId: String)
}