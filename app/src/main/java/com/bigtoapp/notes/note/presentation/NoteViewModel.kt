package com.bigtoapp.notes.note.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.HandleListRequest
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.main.presentation.NavigationCommunication
import com.bigtoapp.notes.main.presentation.NavigationStrategy
import com.bigtoapp.notes.note.data.NoteEditOptions
import com.bigtoapp.notes.note.domain.NoteInteractor
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.presentation.HandleNotesRequest
import com.bigtoapp.notes.notes.presentation.NotesListCommunication

class NoteViewModel(
    private val note: NoteEditOptions.Read,
    private val manageResources: ManageResources,
    private val communications: NoteCommunications,
    private val interactor: NoteInteractor,
    private val handleRequest: HandleListRequest<NoteDomain>,
    private val navigationCommunication: NavigationCommunication.Mutate
): ViewModel(), NoteScreenOperations, ClearError, ObserveNote {

    override fun displayScreenState() {
        val noteId = note.read()
        if(noteId.isEmpty())
            communications.showState(NoteUiState.AddNote)
        else {
            // we get liveData value that already exist to avoid going to DB again
            val noteList = communications.getNotesList()
            val noteDetails = noteList.find { it.mapId(noteId) }!!
            communications.showState(NoteUiState.EditNote(noteDetails))
        }
    }

    override fun saveNote(title: String, subtitle: String) {
        val noteId = note.read()
        if(title.isEmpty() || subtitle.isEmpty())
            communications.showState(NoteUiState.ShowError(manageResources.string(R.string.note_error_message)))
        else {
            if(noteId.isEmpty()){
                handleRequest.handle(viewModelScope){
                    interactor.insertNote(title, subtitle)
                }
                communications.showState(NoteUiState.AddNote)
            } else {
                handleRequest.handle(viewModelScope){
                    interactor.updateNote(noteId, title, subtitle)
                }
                navigationCommunication.put(NavigationStrategy.Back)
            }
        }
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<NoteUiState>) =
        communications.observeState(owner, observer)

    override fun clearError() = communications.showState(NoteUiState.ClearError())
}

interface NoteScreenOperations{
    fun displayScreenState()
    fun saveNote(title: String, subtitle: String)
}

interface ClearError{
    fun clearError()
}