package com.bigtoapp.notes.note.presentation

import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.dialog.presentation.SelectedCategoryCommunications
import com.bigtoapp.notes.main.communications.ShowState
import com.bigtoapp.notes.main.presentation.HandleRequest
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
        private val communications: ShowState<NoteUiState>,
        private val selectedCategory: SelectedCategoryCommunications
    ): HandleInsertNote{
        override fun handleInsert(note: InsertedDomainNote, scope: CoroutineScope) {
            handleRequest.handle(scope){
                interactor.insertNote(note)
            }
            selectedCategory.setSelectedCategory(CategoryData.getDefaultCategory())
            communications.showState(NoteUiState.AddNote)
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



