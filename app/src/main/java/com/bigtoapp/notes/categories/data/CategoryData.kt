package com.bigtoapp.notes.categories.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.dialog.presentation.SelectedCategoryUi
import com.bigtoapp.notes.notes.data.MapperSameId

@Entity(tableName = "categories_table")
data class CategoryData(
    @PrimaryKey
    val id: String,
    val title: String,
    val color: Int
): MapperSameId<String> {
    override fun mapId(id: String): Boolean = this.id == id

    // todo think refactor
    companion object {
        const val DEFAULT_CATEGORY_ID = ""
        const val DEFAULT_CATEGORY_NAME = ""
        const val DEFAULT_CATEGORY_COLOR = 100

        fun getDefaultCategory() =
            SelectedCategoryUi(DEFAULT_CATEGORY_ID, DEFAULT_CATEGORY_NAME, DEFAULT_CATEGORY_COLOR)
    }
}
