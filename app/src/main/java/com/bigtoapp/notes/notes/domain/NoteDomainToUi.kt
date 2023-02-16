package com.bigtoapp.notes.notes.domain

import com.bigtoapp.notes.notes.presentation.DateFormatter
import com.bigtoapp.notes.notes.presentation.NoteUi

class NoteDomainToUi(
    private val dateFormatter: DateFormatter<String, Long>
): NoteDomain.Mapper<NoteUi> {

    override fun map(
        id: String,
        title: String,
        subtitle: String,
        performDate: Long,
        categoryId: String,
        categoryName: String,
        categoryColor: Int
    ) = NoteUi(
        id, title, subtitle, dateFormatter.format(performDate), categoryId, categoryName, categoryColor
    )
}