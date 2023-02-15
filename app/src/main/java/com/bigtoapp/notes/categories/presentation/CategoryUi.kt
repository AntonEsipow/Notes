package com.bigtoapp.notes.categories.presentation

import com.bigtoapp.notes.main.presentation.Mapper
import com.bigtoapp.notes.notes.presentation.MapId

data class CategoryUi(
    private val id: String,
    private val header: String,
    private val color: Int
): Mapper<Boolean, CategoryUi>, MapId {

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, header, color)

    interface Mapper<T>{
        fun map(id: String, header: String, color: Int): T
    }

    // todo refactor
    override fun mapId() = id
    fun mapColor() = color

    override fun map(source: CategoryUi): Boolean = source.id == id
}

class SameCategory(private val source: String): CategoryUi.Mapper<Boolean>{
    override fun map(id: String, header: String, color: Int) = source == id
}