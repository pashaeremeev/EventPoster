package com.example.eventposter.app.ui.profile

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val location = MutableLiveData<Location>().apply {
        value = null
    }
    val text: LiveData<String> = _text
}