package com.bigtoapp.notes.categories.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.BaseFragment
import com.bigtoapp.notes.main.presentation.MenuBaseFragment
import com.bigtoapp.notes.notes.presentation.ItemActions
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CategoriesFragment: MenuBaseFragment<CategoriesViewModel>() {

    override val layoutId = R.layout.fragment_categories
    override val viewModelClass = CategoriesViewModel::class.java
    override val menuId = R.menu.menu_notes

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTitle = viewModel.setFragmentTitle()
        val progressBar = view.findViewById<View>(R.id.progressCategory)
        val emptyState = view.findViewById<TextView>(R.id.categoryEmptyState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.categoryRecyclerView)
        val addCategoryButton = view.findViewById<FloatingActionButton>(R.id.addCategoryButton)
        val adapter = CategoriesAdapter(object: ItemCategoryActions {
            override fun delete(id: String) = viewModel.deleteCategory(id)
            override fun edit(id: String, color: Int) = viewModel.editCategory(id, color)
        })
        recyclerView.adapter = adapter

        addCategoryButton.setOnClickListener {
            viewModel.addCategory()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  if(item.itemId == R.id.menuNotes){
            viewModel.navigateNotes()
            true
        }
        else super.onOptionsItemSelected(item)
    }
}