package com.bigtoapp.notes

import androidx.test.espresso.Espresso
import org.junit.Test

class UpdateAndDeleteNoteTest: BaseTest() {

    @Test
    fun test_update_and_delete_note(){
        val notesPage = NotesPage()
        val notePage = NotePage()

        notesPage.addNoteButton.click()

        notePage.run{
            titleInput.typeText("shop")
            decriptionInput.typeText("milk")
            saveButton.click()

            titleInput.typeText("home")
            decriptionInput.typeText("clean room")
            saveButton.click()
        }
        Espresso.pressBack()

        notesPage.run{
            recyclerView.viewInRecycler(0, titleItem).checkText("shop")
            recyclerView.viewInRecycler(0, descriptionItem).checkText("milk")

            recyclerView.viewInRecycler(1, titleItem).checkText("home")
            recyclerView.viewInRecycler(1, descriptionItem).checkText("clean room")

            recyclerView.viewInRecycler(1, editButton).click()
        }

        notePage.run {
            titleInput.clearText()
            decriptionInput.clearText()

            titleInput.typeText("car")
            decriptionInput.typeText("change the rubber")
            saveButton.click()
        }

        notesPage.run{
            recyclerView.viewInRecycler(0, titleItem).checkText("shop")
            recyclerView.viewInRecycler(0, descriptionItem).checkText("milk")

            recyclerView.viewInRecycler(1, titleItem).checkText("car")
            recyclerView.viewInRecycler(1, descriptionItem).checkText("change the rubber")

            recyclerView.viewInRecycler(0, deleteButton).click()

            recyclerView.viewInRecycler(0, titleItem).checkText("car")
            recyclerView.viewInRecycler(0, descriptionItem).checkText("change the rubber")

            recyclerView.viewInRecycler(0, editButton).click()
        }

        notePage.run {
            titleInput.clearText()
            decriptionInput.clearText()

            titleInput.typeText("grocery")
            decriptionInput.typeText("buy apples")
            saveButton.click()
        }

        notesPage.run{
            recyclerView.viewInRecycler(0, titleItem).checkText("grocery")
            recyclerView.viewInRecycler(0, descriptionItem).checkText("buy apples")
        }
    }
}