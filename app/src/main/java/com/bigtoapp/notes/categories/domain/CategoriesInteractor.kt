package com.bigtoapp.notes.categories.domain

import com.bigtoapp.notes.category.data.CategoryEditOptions
import com.bigtoapp.notes.main.data.EditOptions
import com.bigtoapp.notes.main.data.ListRepository
import com.bigtoapp.notes.main.domain.ListInteractor
import com.bigtoapp.notes.note.data.NoteEditOptions
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.domain.NotesInteractor

class CategoriesInteractor(
    private val repository: ListRepository<List<CategoryDomain>>,
    private val categoryEdit: EditOptions.Save
):ListInteractor<List<CategoryDomain>> {

    override suspend fun all() = repository.all()

    override suspend fun delete(id: String): List<CategoryDomain> {
        repository.delete(id)
        return repository.all()
    }

    override fun edit(id: String) = categoryEdit.save(id)
}