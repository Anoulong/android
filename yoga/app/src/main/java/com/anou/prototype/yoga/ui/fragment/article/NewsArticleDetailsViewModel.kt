package com.anou.prototype.yoga.ui.fragment.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsArticleDetailsViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Article Details Fragment"
    }
    val text: LiveData<String> = _text
}
