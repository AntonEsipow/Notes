package com.bigtoapp.notes.note.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.*
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
): ViewModel(), Init, NoteScreenOperations, ClearError, ObserveNote {

    override fun init(isFirstRun: Boolean) {
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
        if(title.isEmpty())
            communications.showState(
                NoteUiState.ShowErrorTitle(manageResources.string(R.string.title_error_message)))
        else if(subtitle.isEmpty())
            communications.showState(
                NoteUiState.ShowErrorDescription(manageResources.string(R.string.subtitle_error_message)))
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
                note.clear()
                navigationCommunication.put(NavigationStrategy.Back)
            }
        }
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<NoteUiState>) =
        communications.observeState(owner, observer)

    override fun clearError() = communications.showState(NoteUiState.ClearError())
}

interface NoteScreenOperations{
    fun saveNote(title: String, subtitle: String)
}

interface ClearError{
    fun clearError()
}