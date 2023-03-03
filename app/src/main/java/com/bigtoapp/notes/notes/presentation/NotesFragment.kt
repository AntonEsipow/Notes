package com.bigtoapp.notes.notes.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.MenuBaseFragment
import com.bigtoapp.notes.notes.presentation.adapter.AbstractSwipeCallback
import com.bigtoapp.notes.notes.presentation.adapter.ItemActions
import com.bigtoapp.notes.notes.presentation.adapter.NotesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesFragment : MenuBaseFragment<NotesViewModel>() {

    override val layoutId = R.layout.fragment_notes
    override val viewModelClass = NotesViewModel::class.java
    override val menuId = R.menu.menu_categories

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTitle = viewModel.setFragmentTitle()
        val progressBar = view.findViewById<View>(R.id.progressBar)
        val emptyState = view.findViewById<TextView>(R.id.emptyStateTextView)
        val recyclerView = view.findViewById<RecyclerView>(R.id.notesRecyclerView)
        val addNoteButton = view.findViewById<FloatingActionButton>(R.id.addNoteButton)
        val adapter = NotesAdapter(object : ItemActions {
            override fun edit(id: String) = viewModel.editNote(id)
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
        viewModel.init()

        ItemTouchHelper( object : AbstractSwipeCallback(ItemTouchHelper.LEFT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemId = adapter.getItem(position).id()
                viewModel.deleteNote(itemId)
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.menuCategories){
            viewModel.navigateCategories()
            true
        }
        else super.onOptionsItemSelected(item)
    }
}