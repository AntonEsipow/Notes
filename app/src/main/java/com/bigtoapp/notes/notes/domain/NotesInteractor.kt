package com.bigtoapp.notes.notes.domain

import com.bigtoapp.notes.main.data.EditOptions
import com.bigtoapp.notes.main.domain.ListInteractor

class NotesInteractor(
    private val repository: NotesRepository,
    private val noteEdit: EditOptions.Save
): ListInteractor<List<NoteDomain>> {

    override suspend fun all() = repository.allNotes()

    override suspend fun delete(id: String): List<NoteDomain> {
        repository.deleteNote(id)
        return repository.allNotes()
    }

    override fun edit(id: String) = noteEdit.save(id)
}