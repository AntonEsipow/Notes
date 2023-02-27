package com.bigtoapp.notes.dialog.presentation

import com.bigtoapp.notes.main.presentation.adapter.ItemUi

class SelectedCategoryUi(
    private val id: String,
    private val header: String,
    private val color: Int,
    private var selected: Boolean = false
): ItemUi {

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, header, color, selected)

    interface Mapper<T>{
        fun map(id: String, header: String, color: Int, selected: Boolean): T
    }

    fun changeSelected(isSelected: Boolean){ selected = isSelected}

    override fun id() = id
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