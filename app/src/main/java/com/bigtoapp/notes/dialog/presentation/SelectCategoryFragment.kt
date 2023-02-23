package com.bigtoapp.notes.dialog.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.presentation.CategoryUi

class SelectCategoryFragment: BaseDialogFragment<SelectCategoryViewModel>() {

    override val layoutId = R.layout.fragment_select_category
    override val viewModelClass: Class<SelectCategoryViewModel> = SelectCategoryViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryTitle = view.findViewById<TextView>(R.id.selectCategoryTitleText)
        val confirmButton = view.findViewById<Button>(R.id.confirmCategoryButton)
        val processBar = view.findViewById<ProgressBar>(R.id.progressSelect)
        val selectCategoryRecycler = view.findViewById<RecyclerView>(R.id.selectCategoryRecyclerView)
        val adapter = SelectCategoryAdapter(object : SelectedCategoryItem {
            override fun setSelectedItem(categoryUi: CategoryUi) = viewModel.setSelectedCategory(categoryUi)
        })
        selectCategoryRecycler.adapter = adapter

        confirmButton.setOnClickListener {
            dismiss()
        }

        viewModel.observeState(this){
            it.apply(categoryTitle)
        }

        viewModel.observeProgress(this){
            processBar.visibility = it
        }

        viewModel.observeList(this){
            adapter.map(it)
        }

        viewModel.singleInit(savedInstanceState == null)
    }
}