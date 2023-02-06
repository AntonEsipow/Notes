package com.bigtoapp.notes.notes

import android.widget.DatePicker
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.bigtoapp.notes.main.BaseTest
import com.google.android.material.datepicker.MaterialCalendar
import com.google.android.material.datepicker.MaterialDatePicker
import org.junit.Test
import java.util.*

class AddNoteTest: BaseTest() {

    @Test
    fun test_add_note(){
        val notesPage = NotesPage()
        val notePage = NotePage()

        notesPage.addNoteButton.click()

        notePage.run{
            titleInput.typeText("shop")
            descriptionInput.typeText("milk")
            pickDate.click()

            // todo add functionality
            Espresso.onView(withId(com.google.android.material.R.id.confirm_button))
                .perform(click())

            saveButton.click()
        }
        pressBack()

        notesPage.run{
            recyclerView.viewInRecycler(0, titleItem).checkText("shop")
            recyclerView.viewInRecycler(0, descriptionItem).checkText("milk")
        }
    }


}