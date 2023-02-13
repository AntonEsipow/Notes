package com.bigtoapp.notes.notes.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.BaseFragment
import com.bigtoapp.notes.note.presentation.NoteFragment

class NotesFragment : BaseFragment<NotesViewModel>() {

    override val layoutId = R.layout.fragment_notes
    override val viewModelClass = NotesViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val emptyState = view.findViewById<TextView>(R.id.emptyStateTextView)
        val recyclerView = view.findViewById<RecyclerView>(R.id.notesRecyclerView)
        val addNoteButton = view.findViewById<Button>(R.id.addNoteButton)
        val adapter = NotesAdapter(object: NoteActions {
            override fun deleteNote(noteId: String) = viewModel.deleteNote(noteId)
            override fun editNote(noteId: String) = viewModel.editNote(noteId)
        })
        recyclerView.adapter = adapter

        addNoteButton.setOnClickListener {
            viewModel.addNote()
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
        viewModel.init(savedInstanceState == null)
    }
}