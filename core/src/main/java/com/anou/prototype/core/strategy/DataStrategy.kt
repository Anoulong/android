package com.anou.prototype.core.strategy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*

abstract class DataStrategy<T>(val mainScope: CoroutineScope = CoroutineScope(Dispatchers.Main), val localScope: CoroutineScope = CoroutineScope(Dispatchers.IO), val remoteScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) {

    protected val liveData = MediatorLiveData<ResourceWrapper<T>>()
    private val job = start()

    fun cancel() {
        if (mainScope.isActive) {
            mainScope.coroutineContext.cancel()
        }
        if (localScope.isActive) {
            localScope.coroutineContext.cancel()
        }
        if (remoteScope.isActive) {
            remoteScope.coroutineContext.cancel()
        }
        if (job.isActive) {
            job.cancel()
        }
    }
    fun asLiveData(): LiveData<ResourceWrapper<T>> = liveData
    protected abstract fun start(): Job
}