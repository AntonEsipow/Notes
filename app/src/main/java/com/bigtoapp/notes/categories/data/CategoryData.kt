package com.bigtoapp.notes.categories.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bigtoapp.notes.notes.data.MapperSameId

@Entity(tableName = "categories_table")
data class CategoryData(
    @PrimaryKey
    val id: String,
    val title: String
): MapperSameId<String> {
    override fun mapId(id: String): Boolean = this.id == id
}
