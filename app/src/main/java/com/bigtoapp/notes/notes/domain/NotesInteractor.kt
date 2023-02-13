package com.bigtoapp.notes.notes.domain

import com.bigtoapp.notes.main.data.EditOptions
import com.bigtoapp.notes.main.data.ListRepository
import com.bigtoapp.notes.main.domain.ListInteractor

class NotesInteractor(
    private val repository: ListRepository<List<NoteDomain>>,
    private val noteEdit: EditOptions.Save
): ListInteractor<List<NoteDomain>> {

    override suspend fun all() = repository.all()

    override suspend fun delete(id: String): List<NoteDomain> {
        repository.delete(id)
        return repository.all()
    }

    override fun edit(id: String) = noteEdit.save(id)
}