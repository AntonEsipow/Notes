package com.bigtoapp.notes.notes

import androidx.test.espresso.Espresso.pressBack
import com.bigtoapp.notes.main.BaseTest
import org.junit.Test

class UpdateNoteTest: BaseTest() {

    @Test
    fun test_update_note(){
        val notesPage = NotesPage()
        val notePage = NotePage()

        notesPage.addNoteButton.click()

        notePage.run{
            titleInput.typeText("shop")
            descriptionInput.typeText("milk")
            saveButton.click()

            titleInput.typeText("home")
            descriptionInput.typeText("clean room")
            saveButton.click()
        }
        pressBack()

        notesPage.run{
            recyclerView.viewInRecycler(0, titleItem).checkText("shop")
            recyclerView.viewInRecycler(0, descriptionItem).checkText("milk")

            recyclerView.viewInRecycler(1, titleItem).checkText("home")
            recyclerView.viewInRecycler(1, descriptionItem).checkText("clean room")

            recyclerView.viewInRecycler(1, editButton).click()
        }

        notePage.run {
            titleInput.clearText()
            descriptionInput.clearText()

            titleInput.typeText("car")
            descriptionInput.typeText("change the rubber")
            saveButton.click()
        }

        notesPage.run{
            recyclerView.viewInRecycler(0, titleItem).checkText("shop")
            recyclerView.viewInRecycler(0, descriptionItem).checkText("milk")

            recyclerView.viewInRecycler(1, titleItem).checkText("car")
            recyclerView.viewInRecycler(1, descriptionItem).checkText("change the rubber")
        }
    }
}