package com.example.eventposter.app.ui

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class FilterFragment: BottomSheetDialogFragment() {

    open var onFilterApplied: ((FilterModel) -> Unit)? = null

    abstract fun loadFilterSettings(settings: FilterModel)
}

abstract class FilterModel