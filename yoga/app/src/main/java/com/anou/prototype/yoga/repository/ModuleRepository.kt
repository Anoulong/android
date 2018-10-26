package com.anou.prototype.yoga.repository

import androidx.lifecycle.LiveData
import com.anou.prototype.yoga.api.ApiService
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.common.RateLimiter
import com.anou.prototype.yoga.db.ApplicationDatabase
import com.anou.prototype.yoga.db.ModuleEntity
import com.anou.prototype.yoga.strategy.NetworkBoundResource
import com.anou.prototype.yoga.strategy.Resource
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import java.util.concurrent.TimeUnit

/*******************************************************************************
 * QuickSeries® Publishing inc.
 * <p>
 * Copyright (c) 1992-2017 QuickSeries® Publishing inc.
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of QuickSeries®
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with QuickSeries® and QuickSeries's Partners.
 * <p>
 * Created by Anou Chanthavong on 2018-10-25.
 ******************************************************************************/

interface ModuleRepository {
    fun loadModules(): LiveData<Resource<List<ModuleEntity>>>
}

class ModuleRepositoryImpl(
    val dispatcher: AppCoroutineDispatchers,
    val applicationDatabase: ApplicationDatabase,
    val apiService: ApiService
) : ModuleRepository {

    override fun loadModules(): LiveData<Resource<List<ModuleEntity>>> {
        return object : NetworkBoundResource<List<ModuleEntity>, List<ModuleEntity>>(dispatcher) {
            override fun saveCallResult(item: List<ModuleEntity>) {
                applicationDatabase.moduleDao().insertAll(item)
            }

            override fun shouldFetch(data: List<ModuleEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() =    applicationDatabase.moduleDao().retrieveAll()

            override fun createCall() = apiService.fetchModules()

            override fun onFetchFailed() {
//                repoListRateLimit.reset(owner)
            }
        }.asLiveData()
    }

}