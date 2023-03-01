package com.bigtoapp.notes.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.bigtoapp.notes.main.sl.ProvideViewModel

abstract class BaseFragment<T: ViewModel>: Fragment() {

    protected lateinit var viewModel: T
    protected abstract val viewModelClass: Class<T>
    protected abstract val layoutId: Int
    protected lateinit var fragmentTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as ProvideViewModel).provideViewModel(viewModelClass, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    override fun onStart() {
        (activity as MainActivity).supportActionBar?.title = fragmentTitle
        super.onStart()
    }
}