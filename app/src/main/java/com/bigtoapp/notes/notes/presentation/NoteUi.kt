package com.bigtoapp.notes.notes.presentation

import com.bigtoapp.notes.main.presentation.Mapper

data class NoteUi(
    private val id: String,
    private val header: String,
    private val description: String
): Mapper<Boolean, String> {

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, header, description)

    interface Mapper<T>{
        fun map(id: String, header: String, description: String): T
    }

    override fun map(source: String): Boolean = source == id
}
