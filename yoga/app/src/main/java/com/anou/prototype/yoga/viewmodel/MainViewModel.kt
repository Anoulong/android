package com.anou.prototype.yoga.viewmodel

import androidx.lifecycle.ViewModel
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.db.ModuleEntity
import com.anou.prototype.yoga.repository.ModuleRepository
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

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

//    suspend fun getModules(): List<ModuleEntity> {
////     val  modules =  async (dispatchers.network){
////            moduleRepository.getModules()
////        }
////
////        return  modules;
//
//        async(dispatchers.network) {
//            try {
//                moduleRepository.getModules()
//            } catch (error: Throwable) {
//            }
//        }

//    }


}