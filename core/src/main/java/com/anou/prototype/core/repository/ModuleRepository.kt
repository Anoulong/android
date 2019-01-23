package com.anou.prototype.core.repository

import com.anou.prototype.core.api.ApiService
import com.anou.prototype.core.common.AppCoroutineDispatchers
import com.anou.prototype.core.db.ApplicationDatabase
import com.anou.prototype.core.db.ModuleEntity
import kotlinx.coroutines.Deferred

class ModuleRepository (
        val dispatcher: AppCoroutineDispatchers,
        val applicationDatabase: ApplicationDatabase,
        val apiService: ApiService
) : BaseRepository() {


    fun loadModules():  Deferred<List<ModuleEntity>> {
//         val result = MediatorLiveData<List<ModuleEntity>>()

        return apiService.fetchModules()
    }



}