package com.anou.prototype.yoga.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.anou.prototype.yoga.lifecycle.CoroutineLifecycleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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
    private var activitySubscriptions: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add observer so any jobs are automatically cancelled
        lifecycle.addObserver(activityLifecycle)
        Log.d(TAG, "onCreate")
    }

    /**
     * Handle Rx Java subscription
     *
     * @param subscription
     */
    protected fun addDisposable(subscription: Disposable) {
        activitySubscriptions?.add(subscription)
    }

    protected fun clearObservables() {
        activitySubscriptions?.let {
            if (!activitySubscriptions!!.isDisposed) {
                activitySubscriptions?.dispose()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearObservables()

    }
    companion object {
        val TAG = BaseFragment::class.java.simpleName
    }
}