package com.anou.prototype.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.anou.prototype.core.common.AppCoroutineDispatchers
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.core.db.ModuleEntity
import com.anou.prototype.core.repository.ModuleRepository
import com.quickseries.core.remote.ResourceStatus
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(val dispatchers: AppCoroutineDispatchers, val applicationController: ApplicationController, val moduleRepository: ModuleRepository) : BaseViewModel() {

    fun getModules() = moduleRepository.loadModules()

    override fun onCleared() {
        super.onCleared()
        moduleRepository.onJobCancelled()
    }
}