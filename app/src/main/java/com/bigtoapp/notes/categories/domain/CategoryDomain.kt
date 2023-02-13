package com.bigtoapp.notes.categories.domain

data class CategoryDomain(
    private val id: String,
    private val title: String
){
    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, title)

    interface Mapper<T>{
        fun map(id: String, title: String): T
    }
}