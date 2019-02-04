package com.anou.prototype.core.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.anou.prototype.core.common.AppCoroutineDispatchers
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.core.db.ModuleEntity
import com.anou.prototype.core.repository.ModuleRepository
import com.anou.prototype.core.strategy.ResourceStatus
import com.anou.prototype.core.usecase.SideMenuUseCase

class MainViewModel(val dispatchers: AppCoroutineDispatchers, val applicationController: ApplicationController, val moduleRepository: ModuleRepository) : BaseViewModel() {
    val liveModule = MutableLiveData<Boolean>()
    val stateLiveData = MediatorLiveData<SideMenuUseCase>()

    fun getModules(): LiveData<SideMenuUseCase> {

        val source = Transformations.switchMap(liveModule) {
            moduleRepository.loadModules()
        }

        stateLiveData.addSource(source) { result ->
            when (result.status) {
                ResourceStatus.LOADING,
                ResourceStatus.FETCHING -> {
                    stateLiveData.value = SideMenuUseCase.ShowLoading
                    //                        showTransparentProgressDialog()
                }
                ResourceStatus.SUCCESS -> {
                    stateLiveData.value = SideMenuUseCase.ShowSuccess("Bravo")
                    result.value?.let { data ->
                        stateLiveData.value = SideMenuUseCase.SetData(data)

                        if (data.size > 0) {
                            data.get(0).let { firstModule ->
                                stateLiveData.value = SideMenuUseCase.InitializeModule(firstModule)
                                //                                mainRouter.onModuleSelected(activity as MainActivity, firstModule, true)
                            }
                        } else {

                            stateLiveData.value = SideMenuUseCase.ShowEmpty("No modules")
                            //                            Toast.makeText(activity, "", Toast.LENGTH_LONG).show()
                        }

                    }

                    //initialize the first module as the landing screen
                    //                        dismissProgressDialog()
                    stateLiveData.value = SideMenuUseCase.HideLoading

                }
                ResourceStatus.ERROR -> {
                    result.error?.message?.let { errorMessage ->
                        stateLiveData.value = SideMenuUseCase.ShowError(errorMessage)
                    }
                    stateLiveData.value = SideMenuUseCase.HideLoading
                    //                        dismissProgressDialog()
                }
                ResourceStatus.UNKNOWN,
                ResourceStatus.INVALID -> {

                }
            }
        }

        return stateLiveData
    }

    fun refresh(refresh: Boolean? = false) {
        liveModule.value = refresh
    }


    override fun onCleared() {
        super.onCleared()
        moduleRepository.onJobCancelled()
    }
}