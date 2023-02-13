package com.bigtoapp.notes.note.data

import com.bigtoapp.notes.main.data.EditOptions

class NoteEditOptions: EditOptions.Mutable{

    private var value = ""

    override fun save(id: String) {
        value = id
    }

    override fun read() = value

    override fun clear() {
        value = ""
    }
}