package com.bigtoapp.notes.note.presentation

import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bigtoapp.notes.R

class NoteFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val value = requireArguments().getString(KEY)
        view.findViewById<TextView>(R.id.textView).text = value
    }

    companion object{
        private const val KEY = "TEST"

        fun newInstance(value: String) = NoteFragment().apply {
            arguments = Bundle().apply {
                putString(KEY, value)
            }
        }
    }
}