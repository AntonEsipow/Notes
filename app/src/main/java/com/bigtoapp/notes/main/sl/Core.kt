package com.bigtoapp.notes.main.sl

import android.content.Context
import com.bigtoapp.notes.categories.data.BaseCategoriesRepository
import com.bigtoapp.notes.main.data.CacheModule
import com.bigtoapp.notes.main.data.EditOptions
import com.bigtoapp.notes.main.data.ToDoRoomDatabase
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.main.presentation.NavigationCommunication
import com.bigtoapp.notes.note.data.NoteEditOptions
import com.bigtoapp.notes.notes.data.BaseNotesRepository
import com.bigtoapp.notes.notes.presentation.NotesListCommunication

interface Core: CacheModule, ManageResources, ProvideNavigation, ProvideNoteEditOptions,
    ProvideNotesCommunication {

    fun provideDispatchers(): DispatchersList

    fun provideNotesRepository(): BaseNotesRepository

    fun provideCategoriesRepository(): BaseCategoriesRepository

    class Base(
        context: Context,
        private val provideInstances: ProvideInstances
    ): Core{

        private val noteEditOptions = NoteEditOptions()

        private val manageResources = ManageResources.Base(context)

        private val navigationCommunication = NavigationCommunication.Base()

        private val notesListCommunication = NotesListCommunication.Base()

        private val dispatchers by lazy {
            DispatchersList.Base()
        }

        private val cacheModule by lazy {
            provideInstances.provideCacheModule()
        }

        override fun provideNotesRepository() =
            BaseNotesRepository(provideDatabase().notesDao())


        override fun provideCategoriesRepository() =
            BaseCategoriesRepository(provideDatabase().categoriesDao())

        override fun provideDispatchers(): DispatchersList = dispatchers

        override fun provideDatabase(): ToDoRoomDatabase = cacheModule.provideDatabase()

        override fun string(id: Int): String = manageResources.string(id)

        override fun provideNavigation(): NavigationCommunication.Mutable = navigationCommunication

        override fun provideNoteEditOptions(): EditOptions.Mutable = noteEditOptions

        override fun provideNotesListCommunication(): NotesListCommunication = notesListCommunication
    }
}

interface ProvideNavigation{
    fun provideNavigation(): NavigationCommunication.Mutable
}

interface ProvideNoteEditOptions{
    fun provideNoteEditOptions(): EditOptions.Mutable
}

interface ProvideNotesCommunication{
    fun provideNotesListCommunication(): NotesListCommunication
}