package com.bigtoapp.notes.category.presentation

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.BaseFragment
import com.bigtoapp.notes.main.presentation.NavigationStrategy
import com.bigtoapp.notes.main.presentation.SimpleTextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CategoryFragment: BaseFragment<CategoryViewModel>() {

    override val layoutId = R.layout.fragment_category
    override val viewModelClass = CategoryViewModel::class.java

    private lateinit var titleEditText: TextInputEditText

    private val watcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) = viewModel.clearError()
    }

    private val colorWatcher = object : SeekBarOperations() {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) =
            viewModel.updateBackground(
                redSeekBar.progress, greenSeekBar.progress, blueSeekBar.progress
            ){ message, color ->
                title.text = message
                colorView.setBackgroundColor(color)
            }
    }

    private var isInEdit = false
    private var color = 0

    private lateinit var redSeekBar: SeekBar
    private lateinit var greenSeekBar: SeekBar
    private lateinit var blueSeekBar: SeekBar
    private lateinit var colorView: View
    private lateinit var title: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleInputLayout = view.findViewById<TextInputLayout>(R.id.categoryInputLayout)
        titleEditText = view.findViewById(R.id.categoryEditText)
        val saveNoteButton = view.findViewById<Button>(R.id.saveCategoryButton)

        colorView = view.findViewById(R.id.colorView)
        title = view.findViewById(R.id.titleTextView)
        redSeekBar = view.findViewById(R.id.redSeekBar)
        greenSeekBar = view.findViewById(R.id.greenSeekBar)
        blueSeekBar = view.findViewById(R.id.blueSeekBar)

        viewModel.updateBackground(
            redSeekBar.progress, greenSeekBar.progress, blueSeekBar.progress
        ){ message, color ->
            title.text = message
            colorView.setBackgroundColor(color)
        }

        if(arguments!=null){
            isInEdit = true
            color = requireArguments().getInt(NavigationStrategy.BUNDLE_COLOR)
        }

        if(isInEdit){
            val colorInt = color
            viewModel.setCategoryColor(colorInt){ red, green, blue, viewColor, message ->
                redSeekBar.progress = red
                greenSeekBar.progress = green
                blueSeekBar.progress = blue
                colorView.setBackgroundColor(viewColor)
                title.text = message
            }
        }

        viewModel.observeState(this){
            it.apply(titleInputLayout, titleEditText, redSeekBar, greenSeekBar, blueSeekBar)
        }

        saveNoteButton.setOnClickListener {
            color = viewModel
                .setColor(redSeekBar.progress, greenSeekBar.progress, blueSeekBar.progress)
            viewModel.saveCategory(titleEditText.text.toString().trim(), id, color)
        }

        redSeekBar.setOnSeekBarChangeListener(colorWatcher)
        greenSeekBar.setOnSeekBarChangeListener(colorWatcher)
        blueSeekBar.setOnSeekBarChangeListener(colorWatcher)

        viewModel.init(savedInstanceState == null, id)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.run {
            val color = getInt(COLOR_STATE)
            viewModel.setCategoryColor(color){ red, green, blue, viewColor, titleText ->
                redSeekBar.progress = red
                greenSeekBar.progress = green
                blueSeekBar.progress = blue
                colorView.setBackgroundColor(viewColor)
                title.text = titleText
            }
            titleEditText.setText(getString(COLOR_TITLE))
        }
    }

    override fun onResume() {
        titleEditText.addTextChangedListener(watcher)
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        titleEditText.removeTextChangedListener(watcher)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val color = viewModel
            .setColor(redSeekBar.progress, greenSeekBar.progress, blueSeekBar.progress)
        outState.run {
            putInt(COLOR_STATE, color)
            putString(COLOR_TITLE, titleEditText.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    companion object{
        private const val COLOR_STATE = "Saved state"
        private const val COLOR_TITLE = "Saved title"
    }
}

abstract class SeekBarOperations: SeekBar.OnSeekBarChangeListener{
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) = Unit
    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
}