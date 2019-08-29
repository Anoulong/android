package com.anou.prototype.yoga.ui.fragment.rss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RssViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is RSS Fragment"
    }
    val text: LiveData<String> = _text
}