package com.example.eventposter.app.ui

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class FilterFragment: BottomSheetDialogFragment() {

    open var onFilterApplied: ((FilterSettingsModel) -> Unit)? = null

    abstract fun loadFilterSettings(settings: FilterSettingsModel)
}

abstract class FilterSettingsModel