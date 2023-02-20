package com.bigtoapp.notes.main

import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.data.NoteWithCategoryData
import com.bigtoapp.notes.notes.domain.NoteDomain
import com.bigtoapp.notes.notes.presentation.NoteUi

abstract class NotesModelsForTests {

    protected val noteDomain1 = NoteDomain(
        "1", "title", "subtitle", 1L, "1", "shop", 10
    )
    protected val noteUi1 = NoteUi(
        "1","title", "subtitle", "1","1", "shop", 10
    )
    protected val noteData1 = NoteData(
        "1", "title", "subtitle", 2L, 1L, "1"
    )
    private val categoryData1 = CategoryData(
        "1", "shop", 10
    )
    protected val noteWithCategoryData1 = NoteWithCategoryData(
        noteData1, categoryData1
    )


    protected val noteDomain2 = NoteDomain(
        "2", "shop", "apple", 2L, "2", "book", 11
    )
    protected val noteUi2 = NoteUi(
        "2", "shop", "apple", "2", "2", "book", 11
    )
    protected val noteData2 = NoteData(
        "2", "shop", "apple", 1L, 2L, "2"
    )
    protected val categoryData2 = CategoryData(
        "2", "book", 11
    )
    protected val noteWithCategoryData2 = NoteWithCategoryData(
        noteData2, categoryData2
    )
    protected val noteData3 = NoteData(
        "3", "shop", "fish", 1L, 3L, "2"
    )
    protected val noteDomain3 = NoteDomain(
        "3", "shop", "fish", 3L, "2", "book", 11
    )
}