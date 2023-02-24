package com.bigtoapp.notes.note.presentation

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.data.CategoryData
import com.bigtoapp.notes.main.presentation.BaseFragment
import com.bigtoapp.notes.main.presentation.SimpleTextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.time.Duration

class NoteFragment: BaseFragment<NoteViewModel>() {

    override val layoutId = R.layout.fragment_note
    override val viewModelClass: Class<NoteViewModel> = NoteViewModel::class.java

    private lateinit var titleEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var dateText: TextView
    private lateinit var categoryText: TextView

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
        categoryText = view.findViewById(R.id.categoryNameText)
        val selectCategoryButton = view.findViewById<Button>(R.id.selectCategoryButton)

        viewModel.observeState(this){
            it.apply(
                titleInputLayout,
                titleEditText,
                descriptionInputLayout,
                descriptionEditText,
                dateText,
                categoryText
            )
        }

        saveNoteButton.setOnClickListener {
            viewModel.saveNote(
                titleEditText.text.toString().trim(),
                descriptionEditText.text.toString().trim(),
                dateText.text.toString(),
                id
            )
        }

        datePicker.setOnClickListener {
            viewModel.changePerformDate(parentFragmentManager)
        }

        selectCategoryButton.setOnClickListener {
            viewModel.selectCategory(parentFragmentManager)
        }

        viewModel.init(savedInstanceState == null, id)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.run {
            val date = getString(BUNDLE_DATE)
            val category = getString(BUNDLE_CATEGORY)
            dateText.text = date
            categoryText.text = category
        }
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
        outState.run {
            putString(BUNDLE_DATE, dateText.text.toString())
            putString(BUNDLE_CATEGORY, categoryText.text.toString())
        }
    }

    companion object {
        private const val BUNDLE_DATE = "Date"
        private const val BUNDLE_CATEGORY = "Category"
    }
}