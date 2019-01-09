package com.anou.prototype.core.api.strategy

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.anou.prototype.core.api.ResourceStatus
import com.anou.prototype.core.api.ResourceWrapper
import kotlinx.coroutines.*

abstract class RemoteDataFirstStrategy<T>(mainScope: CoroutineScope = CoroutineScope(Dispatchers.Default), localScope: CoroutineScope = CoroutineScope(Dispatchers.IO), remoteScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) : DataStrategy<T>(mainScope = mainScope, localScope = localScope, remoteScope = remoteScope) {

    override fun start(): Job = askRemote()

    private fun askRemote() = mainScope.launch {
        if (isRemoteAvailable()) {
            try {
                liveData.postValue(ResourceWrapper(status = ResourceStatus.FETCHING, localData = false))
                val task = withContext(remoteScope.coroutineContext) { fetchData() }
                val data = task.await()
                liveData.postValue(ResourceWrapper(value = data, status = ResourceStatus.SUCCESS, localData = false))

                withContext(localScope.coroutineContext) { writeData(data) }
            } catch (error: Throwable) {
                askLocal(error)
            }
        } else {
            askLocal(IllegalStateException("Remote data is not available"))
        }
    }

    private fun askLocal(warning: Throwable) = mainScope.launch {
        if (isLocalAvailable()) {
            try {
                liveData.postValue(ResourceWrapper(status = ResourceStatus.LOADING, localData = true, warning = warning))
                val task = withContext(localScope.coroutineContext) { readData() }
                val data = task.await()
                liveData.postValue(ResourceWrapper(value = data, status = ResourceStatus.SUCCESS, localData = true, warning = warning))
            } catch (error: Throwable) {
                liveData.postValue(ResourceWrapper(error = error, status = ResourceStatus.ERROR, localData = true, warning = warning))
            }
        } else {
            liveData.postValue(ResourceWrapper(error = IllegalStateException("Local data is not available"), status = ResourceStatus.INVALID, localData = true, warning = warning))
        }
    }

    @MainThread
    open fun isRemoteAvailable(): Boolean = true

    @MainThread
    open fun isLocalAvailable(): Boolean = true

    @WorkerThread
    abstract suspend fun fetchData(): Deferred<T>

    @WorkerThread
    abstract suspend fun readData(): Deferred<T>

    @WorkerThread
    abstract suspend fun writeData(data: T)
}
