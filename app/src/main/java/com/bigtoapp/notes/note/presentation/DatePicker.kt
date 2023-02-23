package com.bigtoapp.notes.note.presentation

import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.Dialog
import com.bigtoapp.notes.main.presentation.ManageResources
import com.google.android.material.datepicker.MaterialDatePicker

class DatePicker(
    private val manageResources: ManageResources
): Dialog<MaterialDatePicker<Long>> {
    override fun create(): MaterialDatePicker<Long> {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setTitleText(manageResources.string(R.string.calendar))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        return datePicker
    }
}