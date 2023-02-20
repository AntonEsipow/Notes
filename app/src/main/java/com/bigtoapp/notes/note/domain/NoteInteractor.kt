package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.presentation.DateFormatter

interface NoteInteractor {

    suspend fun insertNote(insertedDomainNote: InsertedDomainNote)

    suspend fun updateNote(updatedDomainNote: UpdatedDomainNote)

    class Base(
        private val repository: NoteRepository,
        private val generate: Generate.Mutable<Long>,
        private val dateFormatter: DateFormatter<Long, String>
    ):NoteInteractor {

        override suspend fun insertNote(insertedDomainNote: InsertedDomainNote){
            val mapper = InsertDomainToData(generate, dateFormatter)
            val dataNote = insertedDomainNote.map(mapper)
            repository.insertNote(dataNote)
        }

        override suspend fun updateNote(updatedDomainNote: UpdatedDomainNote) {
            val mapper = UpdateDomainToDataNote(generate, dateFormatter)
            repository.updateNote(updatedDomainNote.map(mapper))
        }
    }
}