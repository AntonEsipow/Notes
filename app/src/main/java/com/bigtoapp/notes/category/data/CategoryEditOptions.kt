package com.bigtoapp.notes.category.data

import com.bigtoapp.notes.main.data.EditOptions

class CategoryEditOptions: EditOptions.Mutable{

    private var value = ""

    override fun save(id: String) {
        value = id
    }

    override fun read() = value

    override fun clear() {
        value = ""
    }
}