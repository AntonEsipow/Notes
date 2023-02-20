package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.note.domain.Generate
import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.presentation.DateFormatter

data class UpdatedDomainNote(
    private val noteId: String,
    private val title: String,
    private val subtitle: String,
    private val date: String,
    private val categoryId: String
) {

    interface Mapper<T>{
        fun map(noteId: String, title: String, subtitle: String, date: String, categoryId: String): T
    }

    fun <T> map(mapper: Mapper<T>) = mapper.map(noteId, title, subtitle, date, categoryId)
}

class UpdateDomainToDataNote(
    private val generate: Generate.Mutable<Long>,
    private val dateFormatter: DateFormatter<Long, String>
): UpdatedDomainNote.Mapper<NoteData>{
    override fun map(
        noteId: String,
        title: String,
        subtitle: String,
        date: String,
        categoryId: String
    ): NoteData {
        val updatedTime = generate.generateTime()
        return NoteData(noteId, title, subtitle,  updatedTime, dateFormatter.format(date), categoryId)
    }
}