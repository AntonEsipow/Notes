package com.bigtoapp.notes.dialog.presentation

import com.bigtoapp.notes.main.presentation.Dialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectCategory: Dialog<BottomSheetDialogFragment> {
    override fun create(): BottomSheetDialogFragment {
        return SelectCategoryFragment()
    }
}