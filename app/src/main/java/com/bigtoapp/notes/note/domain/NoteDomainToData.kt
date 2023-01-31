package com.bigtoapp.notes.note.domain

import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.domain.NoteDomain

class NoteDomainToData: NoteDomain.Mapper<NoteData> {
    override fun map(id: String, title: String, subtitle: String) = NoteData(id, title, subtitle)
}