package com.bigtoapp.notes.notes.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.BaseFragment

class NotesFragment : BaseFragment<NotesViewModel>() {

    override val layoutId = R.layout.fragment_notes
    override val viewModelClass = NotesViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val emptyState = view.findViewById<TextView>(R.id.emptyStateTextView)
        val recyclerView = view.findViewById<RecyclerView>(R.id.notesRecyclerView)
        val addNoteButton = view.findViewById<Button>(R.id.addNoteButton)
        val navigateCategoriesButton = view.findViewById<Button>(R.id.categoriesButton)
        val adapter = NotesAdapter(object: ItemActions {
            override fun delete(id: String) = viewModel.deleteNote(id)
            override fun edit(id: String) = viewModel.editNote(id)
        })
        recyclerView.adapter = adapter

        addNoteButton.setOnClickListener {
            viewModel.addNote()
        }

        navigateCategoriesButton.setOnClickListener {
            viewModel.navigateCategories()
        }

        viewModel.observeState(this){
            it.apply(emptyState)
        }

        viewModel.observeList(this){
            adapter.map(it)
        }

        viewModel.observeProgress(this){
            progressBar.visibility = it
        }
        viewModel.init()
    }
}