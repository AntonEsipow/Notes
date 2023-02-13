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
    val createdTime: Long,
    val performDate: Long
): MapperSameId<String> {

    override fun mapId(id: String): Boolean = this.id == id
}

interface MapperSameId<T>{
    fun mapId(id: T): Boolean
}
