package com.bigtoapp.notes.categories.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.BaseFragment
import com.bigtoapp.notes.notes.presentation.ItemActions

class CategoriesFragment: BaseFragment<CategoriesViewModel>() {

    override val layoutId = R.layout.fragment_categories
    override val viewModelClass = CategoriesViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressCategory)
        val emptyState = view.findViewById<TextView>(R.id.categoryEmptyState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.categoryRecyclerView)
        val addCategoryButton = view.findViewById<Button>(R.id.addCategoryButton)
        val notesButton = view.findViewById<Button>(R.id.notesButton)
        val adapter = CategoriesAdapter(object: ItemActions {
            override fun delete(id: String) = viewModel.deleteCategory(id)
            override fun edit(id: String) = viewModel.editCategory(id)
        })
        recyclerView.adapter = adapter

        addCategoryButton.setOnClickListener {
            viewModel.addCategory()
        }

        notesButton.setOnClickListener {
            viewModel.navigateNotes()
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