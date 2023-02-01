package com.bigtoapp.notes.notes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class NoteData(
    @PrimaryKey
    val id: String,
    val title: String,
    val subtitle: String,
    @ColumnInfo(name = "created_time")
    val createdTime: Long
): MapperSameId<String> {

    override fun mapId(noteId: String): Boolean = noteId == id
}

interface MapperSameId<T>{
    fun mapId(noteId: T): Boolean
}
