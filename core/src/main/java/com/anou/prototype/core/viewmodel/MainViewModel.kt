package com.anou.prototype.core.viewmodel

import com.anou.prototype.core.common.AppCoroutineDispatchers
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.core.repository.ModuleRepository

class MainViewModel(val dispatchers: AppCoroutineDispatchers, val applicationController: ApplicationController, val moduleRepository: ModuleRepository) : BaseViewModel() {

    fun getModules() = moduleRepository.loadModules()

    override fun onCleared() {
        super.onCleared()
        moduleRepository.onJobCancelled()
    }
}