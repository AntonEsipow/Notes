package com.bigtoapp.notes.categories.presentation

import com.bigtoapp.notes.main.presentation.Mapper
import com.bigtoapp.notes.notes.presentation.MapId

data class CategoryUi(
    private val id: String,
    private val header: String
): Mapper<Boolean, CategoryUi>, MapId {

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, header)

    interface Mapper<T>{
        fun map(id: String, header: String): T
    }

    override fun mapId(source: String): Boolean = source == id

    override fun mapId() = id

    override fun map(source: CategoryUi): Boolean = source.id == id
}