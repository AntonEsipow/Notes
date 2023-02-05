package com.bigtoapp.notes.note.presentation

import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.BaseFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class NoteFragment: BaseFragment<NoteViewModel>() {

    override val layoutId = R.layout.fragment_note
    override val viewModelClass: Class<NoteViewModel> = NoteViewModel::class.java

    private lateinit var titleEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText

    private val watcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) = viewModel.clearError()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleInputLayout = view.findViewById<TextInputLayout>(R.id.titleInputLayout)
        titleEditText = view.findViewById(R.id.titleEditText)
        val descriptionInputLayout = view.findViewById<TextInputLayout>(R.id.descriptionInputLayout)
        descriptionEditText = view.findViewById(R.id.descriptionEditText)
        val saveNoteButton = view.findViewById<Button>(R.id.saveNoteButton)
        val datePicker = view.findViewById<TextView>(R.id.datePicker)
        val dateText = view.findViewById<TextView>(R.id.dateText)

        viewModel.observeState(this){
            it.apply(
                titleInputLayout,
                titleEditText,
                descriptionInputLayout,
                descriptionEditText,
                dateText
            )
        }

        saveNoteButton.setOnClickListener {
            viewModel.saveNote(
                titleEditText.text.toString(),
                descriptionEditText.text.toString(),
                dateText.text.toString()
            )
        }

        datePicker.setOnClickListener {
            viewModel.changePerformDate(parentFragmentManager, dateText)
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        titleEditText.addTextChangedListener(watcher)
        descriptionEditText.addTextChangedListener(watcher)
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        titleEditText.removeTextChangedListener(watcher)
        descriptionEditText.removeTextChangedListener(watcher)
    }
}

abstract class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    override fun afterTextChanged(s: Editable?) = Unit
}