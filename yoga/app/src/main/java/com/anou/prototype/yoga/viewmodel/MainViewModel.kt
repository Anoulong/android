package com.anou.prototype.yoga.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.controller.ApplicationController
import com.anou.prototype.yoga.db.module.ModuleEntity
import com.anou.prototype.yoga.repository.ModuleRepository
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.GlobalScope
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
class MainViewModel(val dispatchers: AppCoroutineDispatchers, val applicationController: ApplicationController, val moduleRepository: ModuleRepository) : BaseViewModel() {
    private var result = MediatorLiveData<List<ModuleEntity>>()

    fun getModules(): LiveData<List<ModuleEntity>> {

       viewModelJob = GlobalScope.launch(dispatchers.main, CoroutineStart.DEFAULT) {

            try {

                val modules = withContext(dispatchers.network) {
                    moduleRepository.loadModules()
                }
                result.postValue(modules.await())

            } catch (exception: Exception) {
                applicationController.sendErrorChannel(exception.message.plus("Exception: loadModules"))
            }finally {
                //load data from local
            }

        }
            return  result
    }

    override fun onCleared() {
        super.onCleared()
        moduleRepository.onJobCancelled()
    }
}