package com.bigtoapp.notes.notes.domain

import com.bigtoapp.notes.notes.presentation.NoteUi

class NoteDomainToUi: NoteDomain.Mapper<NoteUi> {
    override fun map(id: String, title: String, subtitle: String) =
        NoteUi(id, title, subtitle)
}