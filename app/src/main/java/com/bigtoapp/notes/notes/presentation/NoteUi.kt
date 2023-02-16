package com.bigtoapp.notes.notes.presentation

import com.bigtoapp.notes.main.presentation.Mapper

data class NoteUi(
    private val id: String,
    private val header: String,
    private val description: String,
    private val performDate: String,
    private val categoryId: String,
    private val categoryName: String,
    private val categoryColor: Int
): Mapper<Boolean, NoteUi>{

    fun <T> map(mapper: Mapper<T>): T =
        mapper.map(id, header, description, performDate, categoryId, categoryName, categoryColor)

    interface Mapper<T>{
        fun map(
            id: String, header: String, description: String, performDate: String,
            categoryId: String, categoryName: String, categoryColor: Int
        ): T
    }

    override fun map(source: NoteUi): Boolean = source.id == id
}

class NoteId: NoteUi.Mapper<String>{
    override fun map(id: String, header: String, description: String, performDate: String,
                     categoryId: String, categoryName: String, categoryColor: Int) = id
}

class SameId(private val source: String): NoteUi.Mapper<Boolean>{
    override fun map(
        id: String,
        header: String,
        description: String,
        performDate: String,
        categoryId: String, categoryName: String, categoryColor: Int
    ) = source == id
}
