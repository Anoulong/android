package com.anou.prototype.core.api

class ResourceWrapper<T>(
    val value: T? = null,
    val status: ResourceStatus = ResourceStatus.UNKNOWN,
    val error: Throwable? = null,
    val localData: Boolean = false,
    val warning: Throwable? = null,
    val timestamp: Long = System.currentTimeMillis()
)
