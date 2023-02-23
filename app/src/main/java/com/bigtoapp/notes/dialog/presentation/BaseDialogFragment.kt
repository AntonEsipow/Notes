package com.bigtoapp.notes.dialog.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.bigtoapp.notes.main.sl.ProvideViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseDialogFragment<T: ViewModel>: BottomSheetDialogFragment() {

    protected lateinit var viewModel: T
    protected abstract val viewModelClass: Class<T>
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as ProvideViewModel).provideViewModel(viewModelClass, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)
}