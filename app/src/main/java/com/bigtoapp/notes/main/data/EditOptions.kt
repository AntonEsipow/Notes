package com.bigtoapp.notes.main.data

interface EditOptions {

    interface Save{
        fun save(id: String)
    }

    interface Read{
        fun read(): String
        fun clear()
    }

    interface Mutable: Save, Read
}