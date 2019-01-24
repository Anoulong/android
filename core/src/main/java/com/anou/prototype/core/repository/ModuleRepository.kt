package com.anou.prototype.core.repository

import androidx.lifecycle.LiveData
import com.anou.prototype.core.api.ApiService
import com.anou.prototype.core.db.ApplicationDatabase
import com.anou.prototype.core.db.ModuleEntity
import com.anou.prototype.core.service.NetworkConnectivityService
import com.anou.prototype.core.strategy.LocalDataAwareFirstStrategy
import com.anou.prototype.core.strategy.ResourceWrapper
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

class ModuleRepository(
        val applicationDatabase: ApplicationDatabase,
        val apiService: ApiService,
        private val networkConnectivityService: NetworkConnectivityService
) : BaseRepository() {

    fun loadModules(): LiveData<ResourceWrapper<List<ModuleEntity>>> = object : LocalDataAwareFirstStrategy<List<ModuleEntity>>() {
//        override fun isRemoteAvailable() = networkConnectivityService.getConnectionType() != NetworkConnectivityService.ConnectionType.TYPE_NO_INTERNET
        override fun isRemoteAvailable() = true

        override suspend fun fetchData(): Deferred<List<ModuleEntity>> {
            return apiService.fetchModules()
        }

        override suspend fun readData(): Deferred<LiveData<List<ModuleEntity>>> {
            return CompletableDeferred(applicationDatabase.moduleDao().loadAllModules())
        }

        override suspend fun writeData(data: List<ModuleEntity>) {
            applicationDatabase.moduleDao().insertAll(data)
        }

    }.asLiveData()
}