package com.anou.prototype.yoga.repository

import android.util.Log
import com.anou.prototype.yoga.base.BaseFragment
import kotlinx.coroutines.experimental.Job
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug


open class BaseRepository : AnkoLogger{
    protected var repositoryJob : Job? = null

    init {
        this.repositoryJob = Job()
        Log.d(TAG, "init repositoryJob")
    }

    fun onJobCancelled() {
        repositoryJob?.cancel()
        Log.d(TAG, "onJobCancelled")
    }


    companion object {
        val TAG = BaseRepository::class.java.simpleName
    }
}