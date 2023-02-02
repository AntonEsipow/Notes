package com.bigtoapp.notes

class NotesPage: Page() {

    val addNoteButton = R.id.addNoteButton.view()
    val recyclerView = R.id.notesRecyclerView

    val titleItem = R.id.titleTextView
    val descriptionItem = R.id.descriptionTextView
    val deleteButton = R.id.deleteNoteButton
    val editButton = R.id.updateNoteButton
}