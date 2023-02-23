package com.bigtoapp.notes.notes.domain

import com.bigtoapp.notes.main.data.MutableListRepository
import com.bigtoapp.notes.main.domain.ListInteractor

class NotesInteractor(
    private val repository: MutableListRepository<List<NoteDomain>>
): ListInteractor<List<NoteDomain>> {

    override suspend fun all() = repository.all()

    override suspend fun delete(id: String): List<NoteDomain> {
        repository.delete(id)
        return repository.all()
    }
}