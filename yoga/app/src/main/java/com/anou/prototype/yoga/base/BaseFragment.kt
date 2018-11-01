package com.anou.prototype.yoga.base

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.anou.prototype.yoga.lifecycle.CoroutineLifecycleObserver
import com.anou.prototype.yoga.repository.BaseRepository
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import org.jetbrains.anko.AnkoLogger

class BaseFragment : Fragment(){
    protected val fragmentLifecycle = CoroutineLifecycleObserver()
    protected val fragmentScope : CoroutineScope = CoroutineScope(Dispatchers.Main + fragmentLifecycle.job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add observer so any jobs are automatically cancelled
        lifecycle.addObserver(fragmentLifecycle)
        Log.d(TAG, "onCreate")
    }

    companion object {
        val TAG = BaseFragment::class.java.simpleName
    }
}