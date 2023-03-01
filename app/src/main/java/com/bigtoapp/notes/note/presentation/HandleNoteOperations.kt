package com.bigtoapp.notes.note.presentation

import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.dialog.presentation.SelectedCategoryCommunications
import com.bigtoapp.notes.main.communications.ShowState
import com.bigtoapp.notes.main.presentation.HandleRequest
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.main.presentation.NavigationCommunication
import com.bigtoapp.notes.main.presentation.NavigationStrategy
import com.bigtoapp.notes.note.domain.InsertedDomainNote
import com.bigtoapp.notes.note.domain.NoteInteractor
import com.bigtoapp.notes.note.domain.UpdatedDomainNote
import kotlinx.coroutines.CoroutineScope

interface HandleInsertNote {

    fun handleInsert(note: InsertedDomainNote, scope: CoroutineScope)

    class Base(
        private val interactor: NoteInteractor,
        private val handleRequest: HandleRequest<Unit>,
        private val showAddNoteState: ShowAddNoteState,
        private val selectedCategory: SelectedCategoryCommunications
    ): HandleInsertNote{
        override fun handleInsert(note: InsertedDomainNote, scope: CoroutineScope) {
            handleRequest.handle(scope){
                interactor.insertNote(note)
            }
            selectedCategory.setSelectedCategory(CategoryData.getDefaultCategory())
            showAddNoteState.showAddNote()
        }
    }
}

interface HandleUpdateNote{

    fun handleUpdate(note: UpdatedDomainNote, scope: CoroutineScope)

    class Base(
        private val interactor: NoteInteractor,
        private val handleRequest: HandleRequest<Unit>,
        private val navigationCommunication: NavigationCommunication.Mutate,
        private val selectedCategory: SelectedCategoryCommunications
    ): HandleUpdateNote{
        override fun handleUpdate(note: UpdatedDomainNote, scope: CoroutineScope) {
            handleRequest.handle(scope){
                interactor.updateNote(note)
            }
            selectedCategory.setSelectedCategory(CategoryData.getDefaultCategory())
            navigationCommunication.put(NavigationStrategy.Back)
        }
    }
}

interface ShowAddNoteState{
    fun showAddNote()

    class Base(
        private val communications: ShowState<NoteUiState>,
        private val manageResources: ManageResources
    ): ShowAddNoteState{

        override fun showAddNote() {
            communications.showState(
                NoteUiState.AddNote(
                    manageResources.string(R.string.select_date),
                    manageResources.string(R.string.choose_category)
                )
            )
        }
    }
}



