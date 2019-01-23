package com.anou.prototype.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.anou.prototype.core.common.AppCoroutineDispatchers
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.core.db.feature.FeatureEntity
import com.anou.prototype.core.repository.FeatureRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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