package com.anou.prototype.yoga.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.anou.prototype.yoga.lifecycle.CoroutineLifecycleObserver
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers

/**
 *  private fun runTask() {
        // Set the coroutine parent from LifecycleObserver so that it will
        // be automatically cancelled in onStop
        launch(parent = activityLifecycle.job) {
            // do something long-running
        }

        launch(parent = activityLifecycle.job) {
            // do something else long-running
        }

        // both of these will be cancelled in onStop (if still running)
    }
 */
open class BaseActivity : AppCompatActivity(){
    protected val activityLifecycle = CoroutineLifecycleObserver()
    protected val activityScope :CoroutineScope = CoroutineScope(Dispatchers.Main + activityLifecycle.job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add observer so any jobs are automatically cancelled
        lifecycle.addObserver(activityLifecycle)
        Log.d(TAG, "onCreate")
    }

    companion object {
        val TAG = BaseFragment::class.java.simpleName
    }
}