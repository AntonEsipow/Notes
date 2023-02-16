package com.bigtoapp.notes.category.presentation

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.category.color.MapBlue
import com.bigtoapp.notes.category.color.MapGreen
import com.bigtoapp.notes.category.color.MapRed
import com.bigtoapp.notes.main.presentation.BaseFragment
import com.bigtoapp.notes.main.presentation.NavigationStrategy
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

    private var isInEdit = false
    private var color = 0

    private lateinit var redSeekBar: SeekBar
    private lateinit var greenSeekBar: SeekBar
    private lateinit var blueSeekBar: SeekBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleInputLayout = view.findViewById<TextInputLayout>(R.id.categoryInputLayout)
        titleEditText = view.findViewById(R.id.categoryEditText)
        val saveNoteButton = view.findViewById<Button>(R.id.saveCategoryButton)

        val colorView = view.findViewById<View>(R.id.colorView)
        val title = view.findViewById<TextView>(R.id.titleTextView)
        redSeekBar = view.findViewById(R.id.redSeekBar)
        greenSeekBar = view.findViewById(R.id.greenSeekBar)
        blueSeekBar = view.findViewById(R.id.blueSeekBar)
        val redMapper = MapRed()
        val greenMapper = MapGreen()
        val blueMapper = MapBlue()

        if(arguments!=null){
            isInEdit = true
            color = requireArguments().getInt(NavigationStrategy.BUNDLE_COLOR)
        }

        if(isInEdit){
            val colorInt = color
            viewModel.setCategoryColor(colorInt){ red, green, blue ->
                redSeekBar.progress = red
                greenSeekBar.progress = green
                blueSeekBar.progress = blue
            }
        }

        if(savedInstanceState != null){
            val color = savedInstanceState.getInt(COLOR_STATE)
            viewModel.setCategoryColor(color){ red, green, blue ->
                redSeekBar.progress = red
                greenSeekBar.progress = green
                blueSeekBar.progress = blue
            }
            titleEditText.setText(savedInstanceState.getString(COLOR_TITLE))
        }

        viewModel.observeState(this){
            it.apply(titleInputLayout, titleEditText, redSeekBar, greenSeekBar, blueSeekBar)
        }

        saveNoteButton.setOnClickListener {
            val red = redSeekBar.progress
            val green = greenSeekBar.progress
            val blue = blueSeekBar.progress
            color = Color.rgb(red, green, blue)
            viewModel.saveCategory(titleEditText.text.toString(), id, color)
        }

        redSeekBar.setOnSeekBarChangeListener(
            SeekBarListener(viewModel::onRedChange)
        )

        greenSeekBar.setOnSeekBarChangeListener(
            SeekBarListener(viewModel::onGreenChange)
        )

        blueSeekBar.setOnSeekBarChangeListener(
            SeekBarListener(viewModel::onBlueChange)
        )

        viewModel.observeColor(this){
            title.text = it.map(it)
            val color = Color.rgb(it.map(redMapper), it.map(greenMapper), it.map(blueMapper))
            colorView.setBackgroundColor(color)
        }

        viewModel.init(savedInstanceState == null, id)
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
        super.onSaveInstanceState(outState)
        val red = redSeekBar.progress
        val green = greenSeekBar.progress
        val blue = blueSeekBar.progress
        val color = Color.rgb(red, green, blue)
        outState.putInt(COLOR_STATE, color)
        outState.putString(COLOR_TITLE, titleEditText.text.toString())
    }

    private class SeekBarListener(
        private val onProgressChange: (Int) -> Unit
    ) : SeekBarOperations() {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onProgressChange(progress)
        }
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