package com.bigtoapp.notes.notes

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.Page

class NotePage: Page() {

    val titleInput = R.id.titleEditText.view()
    val descriptionInput = R.id.descriptionEditText.view()
    val saveButton = R.id.saveNoteButton.view()
    val pickDate = R.id.datePicker.view()
}