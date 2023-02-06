package com.bigtoapp.notes.notes

import androidx.test.espresso.Espresso
import com.bigtoapp.notes.main.BaseTest
import org.junit.Test

class DeleteNoteTest: BaseTest() {

    @Test
    fun test_delete_note(){
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
        Espresso.pressBack()

        notesPage.run{
            recyclerView.viewInRecycler(0, titleItem).checkText("shop")
            recyclerView.viewInRecycler(0, descriptionItem).checkText("milk")

            recyclerView.viewInRecycler(1, titleItem).checkText("home")
            recyclerView.viewInRecycler(1, descriptionItem).checkText("clean room")

            recyclerView.viewInRecycler(0, deleteButton).click()

            recyclerView.viewInRecycler(0, titleItem).checkText("home")
            recyclerView.viewInRecycler(0, descriptionItem).checkText("clean room")
        }
    }
}