package com.anou.prototype.yoga.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.anou.prototype.yoga.lifecycle.CoroutineLifecycleObserver
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers

class BaseFragment : Fragment(){
    protected val fragmentLifecycle = CoroutineLifecycleObserver()
    protected val fragmentScope : CoroutineScope = CoroutineScope(Dispatchers.Main + fragmentLifecycle.job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add observer so any jobs are automatically cancelled
        lifecycle.addObserver(fragmentLifecycle)
    }
}