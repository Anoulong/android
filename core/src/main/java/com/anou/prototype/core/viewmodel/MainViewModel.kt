package com.anou.prototype.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.anou.prototype.core.common.AppCoroutineDispatchers
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.core.db.ModuleEntity
import com.anou.prototype.core.repository.ModuleRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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