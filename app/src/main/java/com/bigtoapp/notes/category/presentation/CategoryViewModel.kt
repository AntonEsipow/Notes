package com.bigtoapp.notes.category.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.presentation.SameCategory
import com.bigtoapp.notes.category.domain.CategoryInteractor
import com.bigtoapp.notes.main.communications.ObserveState
import com.bigtoapp.notes.main.presentation.*
import com.bigtoapp.notes.note.presentation.*
import kotlin.math.roundToInt

class CategoryViewModel(
    private val manageResources: ManageResources,
    private val communications: MutableCategoryCommunications,
    private val interactor: CategoryInteractor,
    private val handleRequest: HandleRequest<Unit>,
    private val navigationCommunication: NavigationCommunication.Mutate
): ViewModel(), CategoryScreenOperations, ClearError, ObserveState<CategoryUiState>,
    SetColors, InitWithId {

    override fun init(isFirstRun: Boolean, id: String) {
        if(isFirstRun){
            if(id.isEmpty())
                communications.showState(CategoryUiState.AddCategory)
            else {
                // we get liveData value that already exist to avoid going to DB again
                val categoryList = communications.getList()
                val mapper = SameCategory(id)
                val categoryDetails = categoryList.find { it.map(mapper) }!!
                communications.showState(CategoryUiState.EditCategory(categoryDetails))
            }
        }
    }

    // todo think refactor
    override fun saveCategory(title: String, categoryId: String, color: Int) {
        if(title.isEmpty())
            communications.showState(
                CategoryUiState.ShowError(manageResources.string(R.string.title_error_message)))
        else {
            if(categoryId.isEmpty()){
                handleRequest.handle(viewModelScope){
                    interactor.insertCategory(title, color)
                }
                communications.showState(CategoryUiState.AddCategory)
            } else {
                handleRequest.handle(viewModelScope){
                    interactor.updateCategory(categoryId, title, color)
                }
                navigationCommunication.put(NavigationStrategy.Back)
            }
        }
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<CategoryUiState>) =
        communications.observeState(owner, observer)

    override fun clearError() = communications.showState(CategoryUiState.ClearError())

    override fun setCategoryColor(categoryColor: Int, colorCallback: (Int, Int, Int, Int, String) -> Unit) {
        val color = Color.valueOf(categoryColor)
        val redValue = (color.red() * 255).roundToInt()
        val greenValue = (color.green() * 255).roundToInt()
        val blueValue = (color.blue() * 255).roundToInt()
        val message = manageResources.string(R.string.color_title_message)
        val text = "$message ($redValue, $greenValue, $blueValue)"
        colorCallback(redValue, greenValue, blueValue, categoryColor, text)
    }

    @SuppressLint("SetTextI18n")
    override fun updateBackground(redValue: Int, greenValue: Int, blueValue: Int, title: TextView, view: View) {
        val color = Color.rgb(redValue, greenValue, blueValue)
        val message = manageResources.string(R.string.color_title_message)
        title.text = "$message ($redValue, $greenValue, $blueValue)"
        view.setBackgroundColor(color)
    }

    override fun setColor(redValue: Int, greenValue: Int, blueValue: Int): Int =
        Color.rgb(redValue, greenValue, blueValue)
}

interface CategoryScreenOperations{
    fun saveCategory(title: String, categoryId: String, color: Int)
}

interface SetColors{
    fun setCategoryColor(categoryColor: Int, colorCallback: (Int, Int, Int, Int, String) -> Unit)
    fun updateBackground(redValue: Int, greenValue: Int, blueValue: Int, title: TextView, view: View)
    fun setColor(redValue: Int, greenValue: Int, blueValue:Int): Int
}