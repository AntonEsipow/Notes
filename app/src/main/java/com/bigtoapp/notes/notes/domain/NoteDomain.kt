package com.bigtoapp.notes.notes.domain

data class NoteDomain(
    private val id: String,
    private val title: String,
    private val subtitle: String,
    private val performDate: Long,
    private val categoryId: String,
    private val categoryName: String,
    private val categoryColor: Int
){
    fun <T> map(mapper: Mapper<T>): T =
        mapper.map(id, title, subtitle, performDate, categoryId, categoryName, categoryColor)

    interface Mapper<T>{
        fun map(id: String, title: String, subtitle: String, performDate: Long,
        categoryId: String, categoryName: String, categoryColor: Int): T
    }
}
