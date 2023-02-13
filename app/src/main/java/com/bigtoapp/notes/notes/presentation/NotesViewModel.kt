package com.bigtoapp.notes.notes.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.main.domain.ListInteractor
import com.bigtoapp.notes.main.presentation.*
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.domain.NotesInteractor

class NotesViewModel(
    private val interactor: ListInteractor<List<NoteDomain>>,
    private val communications: NotesCommunications,
    private val handleRequest: HandleRequest<List<NoteDomain>>,
    private val navigationCommunication: NavigationCommunication.Mutate
): ViewModel(), Init, ObserveNotes, NotesScreenOperations {

    override fun init(isFirstRun: Boolean) {
        if(isFirstRun)
            handleRequest.handle(viewModelScope){
                interactor.all()
            }
    }

    override fun addNote() = navigationCommunication.put(
        NavigationStrategy.ReplaceToBackStack(Screen.Note)
    )

    override fun deleteNote(noteId: String) = handleRequest.handle(viewModelScope){
        interactor.delete(noteId)
    }

    override fun editNote(noteId: String) {
        interactor.edit(noteId)
        navigationCommunication.put(NavigationStrategy.ReplaceToBackStack(Screen.Note))
    }

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        communications.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<NotesUiState>) =
        communications.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NoteUi>>) =
        communications.observeList(owner, observer)
}

interface NotesScreenOperations {
    fun addNote()
    fun deleteNote(noteId: String)
    fun editNote(noteId: String)
}