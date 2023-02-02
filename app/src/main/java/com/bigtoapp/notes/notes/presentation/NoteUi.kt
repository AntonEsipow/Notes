package com.bigtoapp.notes.notes.presentation

import com.bigtoapp.notes.main.presentation.Mapper

data class NoteUi(
    private val id: String,
    private val header: String,
    private val description: String
): Mapper<Boolean, NoteUi>,MapId, GetFields {

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, header, description)

    interface Mapper<T>{
        fun map(id: String, header: String, description: String): T
    }

    override fun mapId(source: String): Boolean = source == id

    override fun mapId() = id

    override fun getTitle(): String = header

    override fun getDescription(): String = description

    override fun map(source: NoteUi): Boolean = source.id == id
}

// todo refactor
interface MapId{
    fun mapId(): String
    fun mapId(source: String): Boolean
}

interface GetFields{
    fun getTitle(): String
    fun getDescription(): String
}
