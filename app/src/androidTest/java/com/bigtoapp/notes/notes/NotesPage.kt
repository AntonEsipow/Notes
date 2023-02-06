package com.bigtoapp.notes.notes

import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.Page

class NotesPage: Page() {

    val addNoteButton = R.id.addNoteButton.view()
    val recyclerView = R.id.notesRecyclerView

    val titleItem = R.id.titleTextView
    val descriptionItem = R.id.descriptionTextView
    val dateItem = R.id.dateTextView
    val deleteButton = R.id.deleteNoteButton
    val editButton = R.id.updateNoteButton
}