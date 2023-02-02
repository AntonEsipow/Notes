package com.bigtoapp.notes

import androidx.test.espresso.Espresso.pressBack
import org.junit.Test

class AddNoteTest: BaseTest() {

    @Test
    fun test_add_note(){
        val notesPage = NotesPage()
        val notePage = NotePage()

        notesPage.addNoteButton.click()

        notePage.run{
            titleInput.typeText("shop")
            decriptionInput.typeText("milk")
            saveButton.click()
        }
        pressBack()

        notesPage.run{
            recyclerView.viewInRecycler(0, titleItem).checkText("shop")
            recyclerView.viewInRecycler(0, descriptionItem).checkText("milk")
        }
    }
}