package com.anou.prototype.core.strategy

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.*

abstract class LocalDataOnlyStrategy<T>(mainScope: CoroutineScope = CoroutineScope(Dispatchers.Default), localScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) : DataStrategy<T>(mainScope = mainScope, localScope = localScope) {

    override fun start(): Job = askLocal()

    // https://proandroiddev.com/android-coroutine-recipes-33467a4302e9
    private fun askLocal() = mainScope.launch {
        if (isLocalAvailable()) {
            try {
                liveData.postValue(ResourceWrapper(status = ResourceStatus.LOADING, localData = true, strategy = this@LocalDataOnlyStrategy::class))
                val task = withContext(localScope.coroutineContext) { readData() }
                val data = task.await()
                liveData.postValue(ResourceWrapper(value = data, status = ResourceStatus.SUCCESS, localData = true, strategy = this@LocalDataOnlyStrategy::class))
            } catch (error: Throwable) {
                liveData.postValue(ResourceWrapper(error = error, status = ResourceStatus.ERROR, localData = true, strategy = this@LocalDataOnlyStrategy::class))
            }
        } else {
            liveData.value = ResourceWrapper(error = IllegalStateException("Local value is not available"), status = ResourceStatus.ERROR, localData = true, strategy = this@LocalDataOnlyStrategy::class)
        }
    }

    @MainThread
    open fun isLocalAvailable(): Boolean = true

    @WorkerThread
    abstract suspend fun readData(): Deferred<T>
}
