package com.bigtoapp.notes.dialog.presentation

import android.view.View
import com.bigtoapp.notes.categories.domain.CategoryDomain
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.main.communications.MutableShow
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.HandleRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HandleSelectCategoryRequest(
    private val dispatchers: DispatchersList,
    private val communications: MutableShow<SelectedCategoryUi, SelectCategoryUiState>,
    private val mapper: CategoryDomain.Mapper<SelectedCategoryUi>,
    private val showState: ShowScreenState<List<CategoryDomain>>
): HandleRequest<List<CategoryDomain>> {

    override fun handle(coroutineScope: CoroutineScope, block: suspend () -> List<CategoryDomain>) {
        communications.showProgress(View.VISIBLE)
        coroutineScope.launch(dispatchers.io()){
            val list = block.invoke()
            communications.showProgress(View.GONE)
            showState.showState(list)
            communications.showList(list.map { it.map(mapper) })
        }
    }
}

class MapCategoryDomainToSelected: CategoryDomain.Mapper<SelectedCategoryUi>{
    override fun map(id: String, title: String, color: Int) =
        SelectedCategoryUi(id,title,color)
}