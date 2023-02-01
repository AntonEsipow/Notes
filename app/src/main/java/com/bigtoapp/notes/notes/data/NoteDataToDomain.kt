package com.bigtoapp.notes.notes.data

import com.bigtoapp.notes.notes.domain.NoteDomain

class NoteDataToDomain: NoteData.Mapper<NoteDomain> {
    override fun map(id: String, title: String, subtitle: String): NoteDomain =
        NoteDomain(id, title, subtitle)
}