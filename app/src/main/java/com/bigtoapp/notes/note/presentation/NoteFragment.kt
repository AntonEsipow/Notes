package com.bigtoapp.notes.note.presentation

import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.BaseFragment

class NoteFragment: BaseFragment<NoteViewModel>() {

    override val layoutId = R.layout.fragment_note
    override val viewModelClass: Class<NoteViewModel> = NoteViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}