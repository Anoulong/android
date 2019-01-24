package com.anou.prototype.core.strategy

import androidx.annotation.WorkerThread
import kotlinx.coroutines.*

abstract class WriteLocalDataStrategy<T>(val value: T, mainScope: CoroutineScope = CoroutineScope(Dispatchers.Default), localScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) : DataStrategy<T>(mainScope = mainScope, localScope = localScope) {

    override fun start() = writeLocal()

    private fun writeLocal() = mainScope.launch {
        try {
            val task = withContext(localScope.coroutineContext) { writeData(value) }
            val data = task.await()
            liveData.postValue(ResourceWrapper(value = data, status = ResourceStatus.SUCCESS, localData = true, strategy = this@WriteLocalDataStrategy::class))
        } catch (error: Throwable) {
            liveData.postValue(ResourceWrapper(error = error, status = ResourceStatus.ERROR, localData = true, strategy = this@WriteLocalDataStrategy::class))
        }
    }

    @WorkerThread
    abstract suspend fun writeData(data: T): Deferred<T>
}
