package com.bigtoapp.notes.main.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.lifecycle.ViewModel

abstract class MenuBaseFragment<T: ViewModel>: BaseFragment<T>() {

    protected var id = ""
    protected abstract val menuId: Int
    protected var isInEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if(arguments!=null){
            isInEdit = true
            id = requireArguments().getString(NavigationStrategy.BUNDLE_ID, "")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(menuId, menu)
    }
}