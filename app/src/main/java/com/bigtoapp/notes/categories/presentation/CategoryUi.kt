package com.bigtoapp.notes.categories.presentation

import com.bigtoapp.notes.main.presentation.Mapper
import com.bigtoapp.notes.main.presentation.adapter.ItemUi

data class CategoryUi(
    private val id: String,
    private val header: String,
    private val color: Int
): ItemUi {

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, header, color)

    interface Mapper<T>{
        fun map(id: String, header: String, color: Int): T
    }

    override fun id() = id
}

class SameCategory(private val source: String): CategoryUi.Mapper<Boolean>{
    override fun map(id: String, header: String, color: Int) = source == id
}

class MapCategoryId: CategoryUi.Mapper<String>{
    override fun map(id: String, header: String, color: Int) = id
}

class MapCategoryName: CategoryUi.Mapper<String>{
    override fun map(id: String, header: String, color: Int) = header
}

class MapCategoryColor: CategoryUi.Mapper<Int>{
    override fun map(id: String, header: String, color: Int) = color
}