package com.bigtoapp.notes.main.sl

import android.content.Context
import com.bigtoapp.notes.categories.data.BaseCategoriesRepository
import com.bigtoapp.notes.categories.presentation.CategoriesListCommunication
import com.bigtoapp.notes.category.presentation.CategoryCommunications
import com.bigtoapp.notes.category.presentation.CategoryUiStateCommunication
import com.bigtoapp.notes.dialog.presentation.SelectedCategory
import com.bigtoapp.notes.dialog.presentation.SelectedCategoryCommunications
import com.bigtoapp.notes.main.data.CacheModule
import com.bigtoapp.notes.main.data.ToDoRoomDatabase
import com.bigtoapp.notes.main.presentation.DispatchersList
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.main.presentation.NavigationCommunication
import com.bigtoapp.notes.note.presentation.NoteCommunications
import com.bigtoapp.notes.note.presentation.NoteUiStateCommunication
import com.bigtoapp.notes.notes.data.BaseNotesRepository
import com.bigtoapp.notes.notes.presentation.NotesListCommunication

interface Core: CacheModule, ManageResources, ProvideNavigation,
    ProvideListCommunication, ProvideCategoryCommunications, ProvideSelectedCategory,
    ProvideNoteCommunications {

    fun provideDispatchers(): DispatchersList

    fun provideNotesRepository(): BaseNotesRepository

    fun provideCategoriesRepository(): BaseCategoriesRepository

    class Base(
        context: Context,
        private val provideInstances: ProvideInstances
    ): Core{

        private val manageResources = ManageResources.Base(context)

        private val navigationCommunication = NavigationCommunication.Base()

        private val notesListCommunication = NotesListCommunication.Base()

        private val categoriesListCommunication = CategoriesListCommunication.Base()

        private val selectedCategory =
            SelectedCategoryCommunications.Base(SelectedCategory.Base())

        // todo remove
        private val categoryCommunications = CategoryCommunications.Base(
            provideCategoriesListCommunication(),
            CategoryUiStateCommunication.Base()
        )

        // todo remove
        private val noteCommunications = NoteCommunications.Base(
            provideNotesListCommunication(),
            NoteUiStateCommunication.Base()
        )

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

        override fun provideNotesListCommunication() = notesListCommunication

        override fun provideCategoriesListCommunication() = categoriesListCommunication

        override fun provideCategoryCommunications(): CategoryCommunications = categoryCommunications

        override fun provideSelectedCategory() = selectedCategory

        // todo refactor
        override fun provideNoteCommunications() = noteCommunications
    }
}

interface ProvideNavigation{
    fun provideNavigation(): NavigationCommunication.Mutable
}

interface ProvideListCommunication{
    fun provideNotesListCommunication(): NotesListCommunication
    fun provideCategoriesListCommunication(): CategoriesListCommunication
}

interface ProvideSelectedCategory{
    fun provideSelectedCategory(): SelectedCategoryCommunications
}

// todo remove, only list provide for select dialog module
interface ProvideCategoryCommunications{
    fun provideCategoryCommunications(): CategoryCommunications
}

interface ProvideNoteCommunications{
    fun provideNoteCommunications(): NoteCommunications
}