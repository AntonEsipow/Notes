package com.bigtoapp.notes.notes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class NoteData(
    @PrimaryKey
    private val id: String,
    private val title: String,
    private val subtitle: String,
    @ColumnInfo(name = "created_time")
    private val createdTime: Long
): MapperSameId<String>, GetId {

    override fun mapId(noteId: String): Boolean = noteId == id

    override fun getId() = id

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, title, subtitle)

    interface Mapper<T>{
        fun map(id: String, title: String, subtitle: String): T
    }
}

interface MapperSameId<T>{
    fun mapId(noteId: T): Boolean
}

interface GetId{
    fun getId(): String
}
