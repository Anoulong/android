package com.anou.prototype.core.repository

import com.anou.prototype.core.api.ApiService
import com.anou.prototype.core.common.AppCoroutineDispatchers
import com.anou.prototype.core.db.ApplicationDatabase
import com.anou.prototype.core.db.category.CategoryEntity
import kotlinx.coroutines.Deferred

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

//interface ModuleRepository {
//     fun loadModules(): Deferred<List<ModuleEntity>>
//}

class CategoryRepository (
        val dispatcher: AppCoroutineDispatchers,
        val applicationDatabase: ApplicationDatabase,
        val apiService: ApiService
) : BaseRepository() {


    fun loadCategory():  Deferred<List<CategoryEntity>> {
//         val result = MediatorLiveData<List<ModuleEntity>>()

        return apiService.fetchCategory()
    }



}