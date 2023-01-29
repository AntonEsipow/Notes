package com.bigtoapp.notes.notes.presentation

data class NoteUi(
    private val id: String,
    private val header: String,
    private val description: String
){
    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, header, description)

    interface Mapper<T>{
        fun map(id: String, header: String, description: String): T
    }
}
