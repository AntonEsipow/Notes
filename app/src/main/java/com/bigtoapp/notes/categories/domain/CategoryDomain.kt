package com.bigtoapp.notes.categories.domain

data class CategoryDomain(
    private val id: String,
    private val title: String,
    private val color: Int
){
    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, title, color)

    interface Mapper<T>{
        fun map(id: String, title: String, color: Int): T
    }
}