package com.bigtoapp.notes.category.presentation

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.BaseFragment
import com.bigtoapp.notes.note.presentation.SimpleTextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CategoryFragment: BaseFragment<CategoryViewModel>() {

    override val layoutId = R.layout.fragment_category
    override val viewModelClass = CategoryViewModel::class.java

    private lateinit var titleEditText: TextInputEditText

    private val watcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) = viewModel.clearError()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleInputLayout = view.findViewById<TextInputLayout>(R.id.categoryInputLayout)
        titleEditText = view.findViewById(R.id.categoryEditText)
        val saveNoteButton = view.findViewById<Button>(R.id.saveCategoryButton)

        viewModel.observeState(this){
            it.apply(titleInputLayout, titleEditText)
        }

        saveNoteButton.setOnClickListener {
            viewModel.saveCategory(titleEditText.text.toString())
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        titleEditText.addTextChangedListener(watcher)
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        titleEditText.removeTextChangedListener(watcher)
    }
}