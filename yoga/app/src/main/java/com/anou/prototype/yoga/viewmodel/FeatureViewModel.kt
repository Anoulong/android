package com.anou.prototype.yoga.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.controller.ApplicationController
import com.anou.prototype.yoga.db.category.CategoryEntity
import com.anou.prototype.yoga.db.feature.FeatureEntity
import com.anou.prototype.yoga.repository.CategoryRepository
import com.anou.prototype.yoga.repository.FeatureRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
class FeatureViewModel(val dispatchers: AppCoroutineDispatchers, val applicationController: ApplicationController, val featureRepository: FeatureRepository) : BaseViewModel() {
    private var result = MediatorLiveData<List<FeatureEntity>>()

    fun getFeatures(): LiveData<List<FeatureEntity>> {

       viewModelJob = GlobalScope.launch(dispatchers.main, CoroutineStart.DEFAULT) {

            try {

                val features = withContext(dispatchers.network) {
                    featureRepository.loadFeatures()
                }
                result.postValue(features.await())

            } catch (exception: Exception) {
                applicationController.sendErrorChannel(exception.message.plus("Exception: getFeatures"))
            }finally {
                //load data from local
            }

        }
            return  result
    }

    override fun onCleared() {
        super.onCleared()
        featureRepository.onJobCancelled()
    }
}