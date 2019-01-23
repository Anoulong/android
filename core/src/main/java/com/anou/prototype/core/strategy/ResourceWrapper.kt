package com.quickseries.core.remote

import com.quickseries.core.remote.strategy.DataStrategy
import kotlin.reflect.KClass

class ResourceWrapper<T>(
        val value: T? = null,
        val status: ResourceStatus = ResourceStatus.UNKNOWN,
        val error: Throwable? = null,
        val localData: Boolean = false,
        val warning: Throwable? = null,
        val timestamp: Long = System.currentTimeMillis(),
        val strategy: KClass<out DataStrategy<T>>

)
