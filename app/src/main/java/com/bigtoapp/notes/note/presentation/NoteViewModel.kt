package com.bigtoapp.notes.note.presentation

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.dialog.presentation.MapSelectedCategoryId
import com.bigtoapp.notes.dialog.presentation.SelectedCategoryCommunications
import com.bigtoapp.notes.main.communications.ObserveState
import com.bigtoapp.notes.main.presentation.*
import com.bigtoapp.notes.note.domain.InsertedDomainNote
import com.bigtoapp.notes.note.domain.NoteInteractor
import com.bigtoapp.notes.note.domain.UpdatedDomainNote
import com.bigtoapp.notes.notes.presentation.DateFormatter
import com.bigtoapp.notes.notes.presentation.MapSelectedCategory
import com.bigtoapp.notes.notes.presentation.SameId
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker

class NoteViewModel(
    private val manageResources: ManageResources,
    private val communications: MutableNoteCommunication,
    private val selectedCategory: SelectedCategoryCommunications,
    private val interactor: NoteInteractor,
    private val handleRequest: HandleRequest<Unit>,
    private val navigationCommunication: NavigationCommunication.Mutate,
    private val dateFormatter: DateFormatter<String, Long>,
    private val dialog: Dialog<MaterialDatePicker<Long>>,
    private val bottomDialog: Dialog<BottomSheetDialogFragment>
): ViewModel(), ClearError, ObserveState<NoteUiState>, PerformDateOperations,
    NoteScreenOperations, InitWithId {

    override fun init(isFirstRun: Boolean, id: String) {
        if(isFirstRun) {
            if(id.isEmpty())
                communications.showState(NoteUiState.AddNote)
            else {
                // we get liveData value that already exist to avoid going to DB again
                val noteList = communications.getList()
                val mapper = SameId(id)
                val noteDetails = noteList.find { it.map(mapper) }!!
                val category = noteDetails.map(MapSelectedCategory())
                selectedCategory.setSelectedCategory(category)
                communications.showState(NoteUiState.EditNote(noteDetails))
            }
        }
    }

    override fun saveNote(title: String, subtitle: String, date: String, noteId: String) {

        val category = selectedCategory.getSelectedCategory()
        val categoryId = category.map(MapSelectedCategoryId())

        if(title.isEmpty())
            communications.showState(
                NoteUiState.ShowErrorTitle(manageResources.string(R.string.title_error_message)))
        else if(subtitle.isEmpty())
            communications.showState(
                NoteUiState.ShowErrorDescription(manageResources.string(R.string.subtitle_error_message)))
        else {
            if(noteId.isEmpty()){
                val note = InsertedDomainNote(title, subtitle, date, categoryId)
                handleRequest.handle(viewModelScope){
                    interactor.insertNote(note)
                }
                selectedCategory.setSelectedCategory(CategoryData.getDefaultCategory())
                communications.showState(NoteUiState.AddNote)
            } else {
                val note = UpdatedDomainNote(noteId, title, subtitle, date, categoryId)
                handleRequest.handle(viewModelScope){
                    interactor.updateNote(note)
                }
                selectedCategory.setSelectedCategory(CategoryData.getDefaultCategory())
                navigationCommunication.put(NavigationStrategy.Back)
            }
        }
    }

    override fun selectCategory(fragmentManager: FragmentManager) {
        val dialog = bottomDialog.create()
        dialog.show(fragmentManager, null)
    }

    override fun changePerformDate(parentFragmentManager: FragmentManager) {
        val datePicker = dialog.create()
        datePicker.show(parentFragmentManager, DATE_PICKER_TAG)
        datePicker.addOnPositiveButtonClickListener {
            // formatting date in dd-mm-yyyy format.
            val date = dateFormatter.format(it)
            communications.showState(NoteUiState.EditDate(date))
        }
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<NoteUiState>) =
        communications.observeState(owner, observer)

    override fun clearError() = communications.showState(NoteUiState.ClearError)

    companion object {
        private const val DATE_PICKER_TAG = "MATERIAL_DATE_PICKER"
    }
}

interface NoteScreenOperations{
    fun saveNote(title: String, subtitle: String, date: String, noteId: String)
    fun selectCategory(fragmentManager: FragmentManager)
}

interface PerformDateOperations{
    fun changePerformDate(parentFragmentManager: FragmentManager)
}

interface ClearError{
    fun clearError()
}