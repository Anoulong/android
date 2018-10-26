package com.anou.prototype.yoga.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anou.prototype.yoga.api.ApiResponse
import com.anou.prototype.yoga.common.AbsentLiveData
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.db.ModuleEntity
import com.anou.prototype.yoga.repository.ModuleRepository
import com.anou.prototype.yoga.strategy.Resource
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

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
class MainViewModel(val dispatchers: AppCoroutineDispatchers, val moduleRepository: ModuleRepository) : ViewModel() {
    private val _login = MutableLiveData<String>()

    val repositories: LiveData<Resource<List<ModuleEntity>>> = Transformations
            .switchMap(_login) { login ->
                if (login == null) {
                    AbsentLiveData.create()
                } else {
                    moduleRepository.loadModules()
                }
            }
}