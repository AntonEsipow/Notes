package com.bigtoapp.notes.category.presentation

import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.BaseFragment

class CategoryFragment: BaseFragment<CategoryViewModel>() {

    override val layoutId = R.layout.fragment_category
    override val viewModelClass = CategoryViewModel::class.java
}