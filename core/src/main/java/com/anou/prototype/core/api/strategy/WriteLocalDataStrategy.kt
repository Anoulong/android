package com.anou.prototype.core.api.strategy

import androidx.annotation.WorkerThread
import com.anou.prototype.core.api.ResourceStatus
import com.anou.prototype.core.api.ResourceWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class WriteLocalDataStrategy<T>(val value: T, mainScope: CoroutineScope = CoroutineScope(Dispatchers.Default), localScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) : DataStrategy<T>(mainScope = mainScope, localScope = localScope) {

    override fun start() = writeLocal()

    private fun writeLocal() = mainScope.launch {
        try {
            val task = withContext(localScope.coroutineContext) { writeData(value) }
            val data = task.await()
            liveData.postValue(ResourceWrapper(value = data, status = ResourceStatus.SUCCESS, localData = true))
        } catch (error: Throwable) {
            liveData.postValue(ResourceWrapper(error = error, status = ResourceStatus.ERROR, localData = true))
        }
    }

    @WorkerThread
    abstract suspend fun writeData(data: T): Deferred<T>
}
