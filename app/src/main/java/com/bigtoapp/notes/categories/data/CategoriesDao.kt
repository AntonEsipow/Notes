package com.bigtoapp.notes.categories.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM categories_table")
    fun allCategories(): List<CategoryData>

    @Query("DELETE FROM categories_table WHERE id = :categoryId")
    fun deleteCategory(categoryId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(categoryData: CategoryData)

    @Query("UPDATE categories_table SET title = :title, color = :color WHERE id = :id")
    fun updateCategory(id: String, title: String, color: Int)
}