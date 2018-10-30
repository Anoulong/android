package com.anou.prototype.yoga.viewmodel

import androidx.lifecycle.*
import com.anou.prototype.yoga.api.ApiResponse
import com.anou.prototype.yoga.common.AbsentLiveData
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.controller.ApplicationController
import com.anou.prototype.yoga.db.ModuleEntity
import com.anou.prototype.yoga.repository.ModuleRepository
import com.anou.prototype.yoga.strategy.Resource
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

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
class MainViewModel(val dispatchers: AppCoroutineDispatchers, val applicationController: ApplicationController, val moduleRepository: ModuleRepository) : ViewModel(), AnkoLogger {
    private var result = MediatorLiveData<List<ModuleEntity>>()

    fun getModules(): LiveData<List<ModuleEntity>> {

        GlobalScope.launch(dispatchers.main, CoroutineStart.DEFAULT) {

            try {

                val modules = withContext(dispatchers.network) {
                    moduleRepository.loadModules()
                }
                result.postValue(modules.await())

            } catch (exception: Exception) {
//                launch { sendString(channel, "foo", 200L) }
                applicationController.sendError(exception.message.plus("Exception: loadModules"))
//                channel.send(exception.message.plus("Exception: loadModules"))
            }finally {
                //load data from local
            }



        }
            return  result
    }
}