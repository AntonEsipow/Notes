package com.bigtoapp.notes.main.data

import android.content.Context
import androidx.room.Room

interface CacheModule {

    fun provideDatabase(): ToDoRoomDatabase

    class Release(private val context: Context): CacheModule {

        private val database by lazy {
            return@lazy Room.databaseBuilder(
                context,
                ToDoRoomDatabase::class.java,
                "todo_database"
            )
                .build()
        }

        override fun provideDatabase(): ToDoRoomDatabase = database
    }

    class Mock(private val context: Context): CacheModule{

        private val database by lazy {
            Room.inMemoryDatabaseBuilder(context, ToDoRoomDatabase::class.java)
                .build()
        }

        override fun provideDatabase(): ToDoRoomDatabase = database
    }
}