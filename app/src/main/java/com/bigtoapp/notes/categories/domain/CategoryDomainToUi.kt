package com.bigtoapp.notes.categories.domain

import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.presentation.DateFormatter
import com.bigtoapp.notes.notes.presentation.NoteUi

class CategoryDomainToUi(): CategoryDomain.Mapper<CategoryUi> {
    override fun map(id: String, title: String) = CategoryUi(id, title)
}