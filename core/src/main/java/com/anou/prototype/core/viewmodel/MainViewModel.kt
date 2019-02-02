package com.anou.prototype.core.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anou.prototype.core.common.AppCoroutineDispatchers
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.core.repository.ModuleRepository
import com.anou.prototype.core.strategy.ResourceStatus
import com.anou.prototype.core.usecase.ModuleUseCase

class MainViewModel(val dispatchers: AppCoroutineDispatchers, val applicationController: ApplicationController, val moduleRepository: ModuleRepository) : BaseViewModel() {

    fun getModules(): LiveData<ModuleUseCase> {
        val stateLiveData = MutableLiveData<ModuleUseCase>()

        try {
            moduleRepository.loadModules().observeForever(Observer { result ->

                when (result.status) {
                    ResourceStatus.LOADING,
                    ResourceStatus.FETCHING -> {
                        stateLiveData.value = ModuleUseCase.ShowLoading
//                        showTransparentProgressDialog()
                    }
                    ResourceStatus.SUCCESS -> {
                        stateLiveData.value = ModuleUseCase.ShowSuccess("Bravo")
                        result.value?.let { data ->
                            stateLiveData.value = ModuleUseCase.SetData(data)

                            if (data.size > 0) {
                                data.get(0).let { firstModule ->
                                    stateLiveData.value = ModuleUseCase.InitializeModule(firstModule)
//                                mainRouter.onModuleSelected(activity as MainActivity, firstModule, true)
                                }
                            } else {

                                stateLiveData.value = ModuleUseCase.ShowEmpty("No modules")
//                            Toast.makeText(activity, "", Toast.LENGTH_LONG).show()
                            }

                        }

                        //initialize the first module as the landing screen
//                        dismissProgressDialog()
                        stateLiveData.value = ModuleUseCase.HideLoading

                    }
                    ResourceStatus.ERROR -> {
                        result.error?.message?.let {errorMessage ->
                            stateLiveData.value = ModuleUseCase.ShowError(errorMessage)
                        }
                        stateLiveData.value = ModuleUseCase.HideLoading
//                        dismissProgressDialog()
                    }
                    ResourceStatus.UNKNOWN,
                    ResourceStatus.INVALID -> {

                    }
                }


            })
        } catch (e: Exception) {
            Log.e("SideMenuFragment", e.message)
        }

        return stateLiveData
    }

    override fun onCleared() {
        super.onCleared()
        moduleRepository.onJobCancelled()
    }
}