package com.bigtoapp.notes.main.sl

import android.content.Context
import com.bigtoapp.notes.main.data.CacheModule
import com.bigtoapp.notes.main.data.ToDoRoomDatabase
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.main.presentation.NavigationCommunication
import com.bigtoapp.notes.note.data.NoteEditOptions
import com.bigtoapp.notes.notes.data.BaseNotesRepository
import com.bigtoapp.notes.notes.domain.NotesRepository
import com.bigtoapp.notes.notes.presentation.NotesListCommunication
import com.bigtoapp.notes.notes.presentation.NotesScreenOperations
import com.bigtoapp.notes.notes.presentation.NotesUiStateCommunication

interface Core: CacheModule, ManageResources, ProvideNavigation, ProvideNoteEditOptions,
    ProvideNotesCommunication {

    fun provideDispatchers(): DispatchersList

    fun provideRepository(): BaseNotesRepository

    class Base(
        context: Context,
        private val provideInstances: ProvideInstances
    ): Core{

        private val noteEditOptions = NoteEditOptions.Base()

        private val manageResources = ManageResources.Base(context)

        private val navigationCommunication = NavigationCommunication.Base()

        private val notesListCommunication = NotesListCommunication.Base()

        private val dispatchers by lazy {
            DispatchersList.Base()
        }

        private val cacheModule by lazy {
            provideInstances.provideCacheModule()
        }

        override fun provideRepository(): BaseNotesRepository {
            return BaseNotesRepository(provideDatabase().notesDao())
        }

        override fun provideDispatchers(): DispatchersList = dispatchers

        override fun provideDatabase(): ToDoRoomDatabase = cacheModule.provideDatabase()

        override fun string(id: Int): String = manageResources.string(id)

        override fun provideNavigation(): NavigationCommunication.Mutable = navigationCommunication

        override fun provideNoteEditOptions(): NoteEditOptions.Mutable = noteEditOptions

        override fun provideNotesListCommunication(): NotesListCommunication = notesListCommunication
    }
}

interface ProvideNavigation{
    fun provideNavigation(): NavigationCommunication.Mutable
}

interface ProvideNoteEditOptions{
    fun provideNoteEditOptions(): NoteEditOptions.Mutable
}

interface ProvideNotesCommunication{
    fun provideNotesListCommunication(): NotesListCommunication
}