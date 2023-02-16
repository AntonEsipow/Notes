package com.bigtoapp.notes.notes.data

import androidx.room.Embedded
import androidx.room.Relation
import com.bigtoapp.notes.categories.data.CategoryData

data class NoteWithCategoryData(
    @Embedded
    val noteData: NoteData,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val categoryData: CategoryData?
)