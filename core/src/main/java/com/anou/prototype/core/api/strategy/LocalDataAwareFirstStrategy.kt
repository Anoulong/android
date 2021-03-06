package com.anou.prototype.core.api.strategy

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.anou.prototype.core.api.ResourceStatus
import com.anou.prototype.core.api.ResourceWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class LocalDataAwareFirstStrategy<T>(mainScope: CoroutineScope = CoroutineScope(Dispatchers.Default), localScope: CoroutineScope = CoroutineScope(Dispatchers.IO), remoteScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) : DataStrategy<T>(mainScope = mainScope, localScope = localScope, remoteScope = remoteScope) {

    override fun start(): Job = askLocal()

    private fun askLocal() = mainScope.launch {
        if (isLocalAvailable()) {
            try {
                liveData.postValue(ResourceWrapper(status = ResourceStatus.LOADING, localData = true))
                val task: Deferred<LiveData<T>> = withContext(localScope.coroutineContext) { readData() }
                val data: LiveData<T> = task.await()

                CoroutineScope(Dispatchers.Main).launch {
                    liveData.addSource(data) { value ->
                        liveData.postValue(ResourceWrapper(value = value, status = ResourceStatus.SUCCESS, localData = true))
                    }
                }
                askRemote()
            } catch (error: Throwable) {
                askRemote(warning = error)
            }
        } else {
            askRemote(warning = IllegalStateException("Local value is not available"))
        }
    }

    private fun askRemote(warning: Throwable? = null) = mainScope.launch {
        if (isRemoteAvailable()) {
            try {
                liveData.postValue(ResourceWrapper(status = ResourceStatus.FETCHING, localData = false, warning = warning))
                val task = withContext(remoteScope.coroutineContext) { fetchData() }
                val data = task.await()
                withContext(localScope.coroutineContext) { writeData(data) }
            } catch (error: Throwable) {
                liveData.postValue(ResourceWrapper(error = error, status = ResourceStatus.ERROR, localData = false, warning = warning))
            }
        } else {
            liveData.postValue(ResourceWrapper(error = IllegalStateException("Remote value is not available"), status = ResourceStatus.INVALID, localData = false, warning = warning))
        }
    }

    @MainThread
    open fun isRemoteAvailable(): Boolean = true

    @MainThread
    open fun isLocalAvailable(): Boolean = true

    @WorkerThread
    abstract suspend fun fetchData(): Deferred<T>

    @WorkerThread
    abstract suspend fun readData(): Deferred<LiveData<T>>

    @WorkerThread
    abstract suspend fun writeData(data: T)
}
