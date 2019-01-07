package com.anou.prototype.yoga.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.controller.ApplicationController
import com.anou.prototype.yoga.db.category.CategoryEntity
import com.anou.prototype.yoga.repository.CategoryRepository
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
class FeatureViewModel(val dispatchers: AppCoroutineDispatchers, val applicationController: ApplicationController, val categoryRepository: CategoryRepository) : BaseViewModel() {
    private var result = MediatorLiveData<List<CategoryEntity>>()

    fun getFaqs(): LiveData<List<CategoryEntity>> {

       viewModelJob = GlobalScope.launch(dispatchers.main, CoroutineStart.DEFAULT) {

            try {

                val faqs = withContext(dispatchers.network) {
//                    categoryRepository.loadFaqs()
                }
//                result.postValue(faqs.await())

            } catch (exception: Exception) {
                applicationController.sendErrorChannel(exception.message.plus("Exception: getFaqs"))
            }finally {
                //load data from local
            }

        }
            return  result
    }

    override fun onCleared() {
        super.onCleared()
        categoryRepository.onJobCancelled()
    }
}