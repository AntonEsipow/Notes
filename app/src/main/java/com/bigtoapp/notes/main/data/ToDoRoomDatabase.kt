package com.bigtoapp.notes.main.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.data.cache.NotesDao

@Database(entities = [NoteData::class], version = 1)
abstract class ToDoRoomDatabase: RoomDatabase() {

    abstract fun notesDao(): NotesDao
}