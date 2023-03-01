package com.bigtoapp.notes.note.presentation

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.MenuBaseFragment
import com.bigtoapp.notes.main.presentation.SimpleTextWatcher
import com.bigtoapp.notes.main.views.BaseCustomTextInputEditText
import com.bigtoapp.notes.main.views.BaseCustomTextInputLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NoteFragment: MenuBaseFragment<NoteViewModel>() {

    override val layoutId = R.layout.fragment_note
    override val viewModelClass: Class<NoteViewModel> = NoteViewModel::class.java
    override val menuId: Int = R.menu.menu_add_note

    private lateinit var titleEditText: BaseCustomTextInputEditText
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var dateText: TextView
    private lateinit var categoryText: TextView
    private lateinit var noteLayout: LinearLayout

    private val watcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) = viewModel.clearError()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleInputLayout = view.findViewById<BaseCustomTextInputLayout>(R.id.titleInputLayout)
        titleEditText = view.findViewById(R.id.titleEditText)
        val descriptionInputLayout = view.findViewById<TextInputLayout>(R.id.descriptionInputLayout)
        descriptionEditText = view.findViewById(R.id.descriptionEditText)
        dateText = view.findViewById(R.id.dateText)
        categoryText = view.findViewById(R.id.categoryNameText)
        noteLayout = view.findViewById(R.id.noteLayout)

        viewModel.observeState(this){
            it.apply(
                titleInputLayout,
                titleEditText,
                descriptionInputLayout,
                descriptionEditText,
                dateText,
                categoryText,
                noteLayout
            )
        }

        viewModel.init(savedInstanceState == null, id)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.run {
            val date = getString(BUNDLE_DATE)
            val category = getString(BUNDLE_CATEGORY)
            val color = getInt(BUNDLE_COLOR)
            dateText.text = date
            categoryText.text = category
            noteLayout.setBackgroundColor(color)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menuEditPerformDate -> {
                viewModel.changePerformDate(parentFragmentManager)
                true
            }
            R.id.menuEditCategory -> {
                viewModel.selectCategory(parentFragmentManager)
                true
            }
            R.id.menuNoteSave -> {
                viewModel.saveNote(
                    titleEditText.text.toString().trim(),
                    descriptionEditText.text.toString().trim(),
                    dateText.text.toString(),
                    id
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val color = (noteLayout.background as ColorDrawable).color
        outState.run {
            putString(BUNDLE_DATE, dateText.text.toString())
            putString(BUNDLE_CATEGORY, categoryText.text.toString())
            putInt(BUNDLE_COLOR, color)
        }
    }

    companion object {
        private const val BUNDLE_DATE = "Date"
        private const val BUNDLE_CATEGORY = "Category"
        private const val BUNDLE_COLOR = "Color"
    }
}