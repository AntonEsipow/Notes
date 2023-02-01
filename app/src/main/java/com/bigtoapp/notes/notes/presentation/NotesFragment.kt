package com.bigtoapp.notes.notes.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.BaseFragment
import com.bigtoapp.notes.note.presentation.NoteFragment

class NotesFragment : BaseFragment<NotesViewModel>() {

    override val layoutId = R.layout.fragment_notes
    override val viewModelClass: Class<NotesViewModel> = NotesViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
        view.findViewById<View>(R.id.addNoteButton)
    }
}