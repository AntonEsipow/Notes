package com.bigtoapp.notes.dialog.presentation

import com.bigtoapp.notes.main.presentation.Mapper

class SelectedCategoryUi(
    private val id: String,
    private val header: String,
    private val color: Int,
    private var selected: Boolean = false
): Mapper<Boolean, SelectedCategoryUi> {

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, header, color, selected)

    interface Mapper<T>{
        fun map(id: String, header: String, color: Int, selected: Boolean): T
    }

    override fun map(source: SelectedCategoryUi): Boolean = source.id == id

    fun changeSelected(isSelected: Boolean){ selected = isSelected}
}

class MapSelectedCategoryId: SelectedCategoryUi.Mapper<String>{
    override fun map(id: String, header: String, color: Int, selected: Boolean) = id
}

class MapSelectedCategoryName: SelectedCategoryUi.Mapper<String>{
    override fun map(id: String, header: String, color: Int, selected: Boolean) = header
}

class MapSelectedCategoryColor: SelectedCategoryUi.Mapper<Int>{
    override fun map(id: String, header: String, color: Int, selected: Boolean) = color
}