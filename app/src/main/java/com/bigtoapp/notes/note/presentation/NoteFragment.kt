package com.bigtoapp.notes.note.presentation

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.main.presentation.BaseFragment
import com.bigtoapp.notes.main.presentation.SimpleTextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NoteFragment: BaseFragment<NoteViewModel>() {

    override val layoutId = R.layout.fragment_note
    override val viewModelClass: Class<NoteViewModel> = NoteViewModel::class.java

    private lateinit var titleEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var dateText: TextView

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
        val datePicker = view.findViewById<Button>(R.id.datePicker)
        dateText = view.findViewById(R.id.dateText)

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
                dateText.text.toString(),
                id,
                // todo remove and add
                CategoryData.DEFAULT_CATEGORY_ID
            )
        }

        datePicker.setOnClickListener {
            viewModel.changePerformDate(childFragmentManager, dateText)
        }

        viewModel.init(savedInstanceState == null, id)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val date = savedInstanceState?.getString(BUNDLE_KEY, DEFAULT_VAL_DATE)!!
        dateText.text = date
        super.onViewStateRestored(savedInstanceState)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(BUNDLE_KEY, dateText.text.toString())
    }

    companion object {
        private const val BUNDLE_KEY = "Date"
        private const val DEFAULT_VAL_DATE = ""
    }
}