package com.bigtoapp.notes.note.presentation

import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.*
import com.bigtoapp.notes.note.domain.NoteInteractor
import com.bigtoapp.notes.notes.presentation.DateFormatter
import com.bigtoapp.notes.notes.presentation.SameId
import com.google.android.material.datepicker.MaterialDatePicker

class NoteViewModel(
    private val manageResources: ManageResources,
    private val communications: NoteCommunications,
    private val interactor: NoteInteractor,
    private val handleRequest: HandleRequest<Unit>,
    private val navigationCommunication: NavigationCommunication.Mutate,
    private val dateFormatter: DateFormatter<String, Long>,
    private val dialog: Dialog<MaterialDatePicker<Long>>
): ViewModel(), ClearError, ObserveNote, PerformDateOperations, NoteScreenOperations, InitWithId {

    override fun init(isFirstRun: Boolean, id: String) {
        if(isFirstRun) {
            if(id.isEmpty())
                communications.showState(NoteUiState.AddNote)
            else {
                // we get liveData value that already exist to avoid going to DB again
                val noteList = communications.getList()
                val mapper = SameId(id)
                val noteDetails = noteList.find { it.map(mapper) }!!
                communications.showState(NoteUiState.EditNote(noteDetails))
            }
        }
    }

    override fun saveNote(title: String, subtitle: String, date: String, noteId: String) {
        if(title.isEmpty())
            communications.showState(
                NoteUiState.ShowErrorTitle(manageResources.string(R.string.title_error_message)))
        else if(subtitle.isEmpty())
            communications.showState(
                NoteUiState.ShowErrorDescription(manageResources.string(R.string.subtitle_error_message)))
        else {
            if(noteId.isEmpty()){
                handleRequest.handle(viewModelScope){
                    interactor.insertNote(title, subtitle, date)
                }
                communications.showState(NoteUiState.AddNote)
            } else {
                handleRequest.handle(viewModelScope){
                    interactor.updateNote(noteId, title, subtitle, date)
                }
                navigationCommunication.put(NavigationStrategy.Back)
            }
        }
    }

    override fun changePerformDate(parentFragmentManager: FragmentManager, view: TextView) {
        val datePicker = dialog.create()
        datePicker.show(parentFragmentManager, DATE_PICKER_TAG)
        datePicker.addOnPositiveButtonClickListener {
            // formatting date in dd-mm-yyyy format.
            view.text = dateFormatter.format(it)
        }
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<NoteUiState>) =
        communications.observeState(owner, observer)

    override fun clearError() = communications.showState(NoteUiState.ClearError())

    companion object {
        private const val DATE_PICKER_TAG = "MATERIAL_DATE_PICKER"
    }
}

interface NoteScreenOperations{
    fun saveNote(title: String, subtitle: String, date: String, noteId: String)
}

interface PerformDateOperations{
    fun changePerformDate(parentFragmentManager: FragmentManager, view: TextView)
}

interface ClearError{
    fun clearError()
}