package com.bigtoapp.notes.note.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.note.data.NoteEditOptions
import com.bigtoapp.notes.note.domain.NoteInteractor
import com.bigtoapp.notes.notes.presentation.NotesListCommunication

class NoteViewModel(
    private val note: NoteEditOptions.Read,
    private val manageResources: ManageResources,
    private val communications: NoteCommunications,
    private val interactor: NoteInteractor,
    private val handleRequest: HandleNoteRequest
): ViewModel(), NoteScreenOperations, ClearError, ObserveNote {

    override fun displayScreenState() {
        val noteId = note.read()
        if(noteId.isEmpty())
            communications.showState(NoteUiState.AddNote)
        else {
            val noteList = communications.getNotesList()
            val noteDetails = noteList.find { it.map(noteId) }!!
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
                // todo refreshScreen
            } else {
                handleRequest.handle(viewModelScope){
                    interactor.updateNote(noteId, title, subtitle)
                }
                // todo navigate back
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