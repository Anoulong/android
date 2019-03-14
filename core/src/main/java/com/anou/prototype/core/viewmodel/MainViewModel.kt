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

    fun getModules(): LiveData<SideMenuUseCase> {
        //initialize source to be triggered
        val liveSource = MutableLiveData<SideMenuUseCase>()
        liveSource.value = SideMenuUseCase.ShowEmpty("")

        val liveUseCase = MediatorLiveData<SideMenuUseCase>()

        val source = Transformations.switchMap(liveSource) {
            moduleRepository.loadModules()
        }

        liveUseCase.addSource(source) { result ->
            when (result.status) {
                ResourceStatus.LOADING,
                ResourceStatus.FETCHING -> {
                    liveUseCase.value = SideMenuUseCase.ShowLoading
                    //                        showTransparentProgressDialog()
                }
                ResourceStatus.SUCCESS -> {
                    liveUseCase.value = SideMenuUseCase.ShowSuccess("Bravo")
                    result.value?.let { data ->
                        liveUseCase.value = SideMenuUseCase.SetData(data)

                        if (data.size > 0) {
                            data.get(0).let { firstModule ->
                                liveUseCase.value = SideMenuUseCase.InitializeModule(firstModule)
                                //                                mainRouter.onModuleSelected(activity as MainActivity, firstModule, true)
                            }
                        } else {

                            liveUseCase.value = SideMenuUseCase.ShowEmpty("No modules")
                            //                            Toast.makeText(activity, "", Toast.LENGTH_LONG).show()
                        }
                    }

                    //initialize the first module as the landing screen
                    //                        dismissProgressDialog()
                    liveUseCase.value = SideMenuUseCase.HideLoading

                }
                ResourceStatus.ERROR -> {
                    result.error?.message?.let { errorMessage ->
                        liveUseCase.value = SideMenuUseCase.ShowError(errorMessage)
                    }
                    liveUseCase.value = SideMenuUseCase.HideLoading
                    //                        dismissProgressDialog()
                }
                ResourceStatus.UNKNOWN,
                ResourceStatus.INVALID -> {

                }
            }
        }

        return liveUseCase
    }

    override fun onCleared() {
        super.onCleared()
        moduleRepository.onJobCancelled()
    }
}