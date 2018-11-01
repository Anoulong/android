package com.anou.prototype.yoga.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.repository.BaseRepository
import kotlinx.coroutines.experimental.Job
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

open class BaseViewModel :  ViewModel(), AnkoLogger {
    protected var viewModelJob : Job? = null

    init {
        this.viewModelJob = Job()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared")
    }
    companion object {
        val TAG = BaseViewModel::class.java.simpleName
    }
}