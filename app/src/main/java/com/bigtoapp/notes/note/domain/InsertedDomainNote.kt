package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.note.domain.Generate
import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.presentation.DateFormatter

data class InsertedDomainNote(
    private val title: String,
    private val subtitle: String,
    private val date: String,
    private val categoryId: String
) {

    interface Mapper<T>{
        fun map(title: String, subtitle: String, date: String, categoryId: String): T
    }

    fun <T> map(mapper: Mapper<T>) = mapper.map(title, subtitle, date, categoryId)
}

class InsertDomainToData(
    private val generate: Generate.Mutable<Long>,
    private val dateFormatter: DateFormatter<Long, String>
): InsertedDomainNote.Mapper<NoteData>{

    override fun map(
        title: String,
        subtitle: String,
        date: String,
        categoryId: String
    ): NoteData {
        val noteId = generate.generateId()
        val createdTime = generate.generateTime()
        return NoteData(noteId, title, subtitle, createdTime, dateFormatter.format(date), categoryId)
    }
}