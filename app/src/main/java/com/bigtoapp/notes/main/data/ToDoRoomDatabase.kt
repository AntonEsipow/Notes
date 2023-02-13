package com.bigtoapp.notes.main.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bigtoapp.notes.categories.data.CategoriesDao
import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.data.NotesDao

@Database(entities = [NoteData::class, CategoryData::class], version = 1)
abstract class ToDoRoomDatabase: RoomDatabase() {

    abstract fun notesDao(): NotesDao

    abstract fun categoriesDao(): CategoriesDao
}