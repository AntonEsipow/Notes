package com.bigtoapp.notes.note.data

interface NoteEditOptions {

    interface Save{
        fun save(id: String)
    }

    interface Read{
        fun read(): String
    }

    interface Mutable: Save, Read

    // todo think how to do
    class Base: Mutable{

        private var value = ""

        override fun save(id: String) {
            value = id
        }

        override fun read(): String {
            val data = value
            value = ""
            return data
        }
    }
}