package com.bigtoapp.notes.categories.presentation

import android.view.View
import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.HandleRequest
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.presentation.NoteUi
import com.bigtoapp.notes.notes.presentation.NotesCommunications
import com.bigtoapp.notes.notes.presentation.NotesUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HandleCategoriesRequest(
    private val dispatchers: DispatchersList,
    private val communications: CategoriesCommunications,
    private val mapper: CategoryDomain.Mapper<CategoryUi>
): HandleRequest<List<CategoryDomain>> {

    override fun handle(coroutineScope: CoroutineScope, block: suspend () -> List<CategoryDomain>) {
        communications.showProgress(View.VISIBLE)
        coroutineScope.launch(dispatchers.io()) {
            val list = block.invoke()
            communications.showProgress(View.GONE)
            // todo move out of here
            communications.showState(
                if(list.isEmpty())
                    CategoriesUiState.NoCategories
                else
                    CategoriesUiState.Categories
            )
            communications.showList(list.map { it.map(mapper) })
        }
    }
}