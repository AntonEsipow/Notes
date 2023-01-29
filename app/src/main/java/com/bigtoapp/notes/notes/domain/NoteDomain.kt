package com.bigtoapp.notes.notes.domain

data class NoteDomain(
    private val id: String,
    private val title: String,
    private val subtitle: String
){
    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, title, subtitle)

    interface Mapper<T>{
        fun map(id: String, title: String, subtitle: String): T
    }
}
