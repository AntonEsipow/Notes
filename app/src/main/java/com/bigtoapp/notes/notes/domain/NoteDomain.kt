package com.bigtoapp.notes.notes.domain

data class NoteDomain(
    private val id: String,
    private val title: String,
    private val subtitle: String,
    private val performDate: Long
){
    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, title, subtitle, performDate)

    interface Mapper<T>{
        fun map(id: String, title: String, subtitle: String, performDate: Long): T
    }
}
