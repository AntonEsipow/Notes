package com.bigtoapp.notes.category.presentation

import android.graphics.Color
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.presentation.SameCategory
import com.bigtoapp.notes.category.color.ColorCommunication
import com.bigtoapp.notes.category.color.ColorCommunications
import com.bigtoapp.notes.category.color.ColorState
import com.bigtoapp.notes.category.color.ObserveColor
import com.bigtoapp.notes.category.domain.CategoryInteractor
import com.bigtoapp.notes.main.presentation.*
import com.bigtoapp.notes.note.presentation.*
import kotlin.math.roundToInt

class CategoryViewModel(
    private val manageResources: ManageResources,
    private val communications: CategoryCommunications,
    private val colorCommunications: ColorCommunications,
    private val interactor: CategoryInteractor,
    private val handleRequest: HandleRequest<Unit>,
    private val navigationCommunication: NavigationCommunication.Mutate
): ViewModel(), CategoryScreenOperations, ClearError, ObserveCategory,
    ObserveColor, ColorChange, SetColors, InitWithId {

    override fun init(isFirstRun: Boolean, id: String) {
        if(isFirstRun){
            if(id.isEmpty())
                communications.showState(CategoryUiState.AddCategory(colorCommunications))
            else {
                // we get liveData value that already exist to avoid going to DB again
                val categoryList = communications.getList()
                val mapper = SameCategory(id)
                val categoryDetails = categoryList.find { it.map(mapper) }!!
                communications.showState(CategoryUiState.EditCategory(categoryDetails))
            }
        }
    }

    override fun saveCategory(title: String, categoryId: String, color: Int) {
        if(title.isEmpty())
            communications.showState(
                CategoryUiState.ShowError(manageResources.string(R.string.title_error_message)))
        else {
            if(categoryId.isEmpty()){
                handleRequest.handle(viewModelScope){
                    interactor.insertCategory(title, color)
                }
                communications.showState(CategoryUiState.AddCategory(colorCommunications))
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

    override fun observeColor(owner: LifecycleOwner, observer: Observer<ColorState>) =
        colorCommunications.observeColor(owner, observer)

    override fun onRedChange(red: Int){
        colorCommunications.showColor(colorCommunications.emptyGet().copy(red = red))
    }

    override fun onGreenChange(green: Int){
        colorCommunications.showColor(colorCommunications.emptyGet().copy(green = green))
    }

    override fun onBlueChange(blue: Int){
        colorCommunications.showColor(colorCommunications.emptyGet().copy(blue = blue))
    }

    override fun setCategoryColor(categoryColor: Int, colorCallback: (Int, Int, Int) -> Unit) {

        val color = Color.valueOf(categoryColor)
        val redValue = (color.red() * 255).roundToInt()
        val greenValue = (color.green() * 255).roundToInt()
        val blueValue = (color.blue() * 255).roundToInt()

        colorCallback(redValue, greenValue, blueValue)

        colorCommunications.showColor(ColorState(
            redValue,
            greenValue,
            blueValue
        ))
    }
}

interface CategoryScreenOperations{
    fun saveCategory(title: String, categoryId: String, color: Int)
}

interface ColorChange{
    fun onRedChange(red: Int)
    fun onGreenChange(green: Int)
    fun onBlueChange(blue: Int)
}

interface SetColors{
    fun setCategoryColor(categoryColor: Int, colorCallback: (Int, Int, Int) -> Unit)
}