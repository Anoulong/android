package com.anou.prototype.core.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.anou.prototype.core.common.AppCoroutineDispatchers
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.core.db.category.CategoryEntity
import com.anou.prototype.core.repository.CategoryRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(val dispatchers: AppCoroutineDispatchers, val applicationController: ApplicationController, val categoryRepository: CategoryRepository) : BaseViewModel() {
    private var result = MediatorLiveData<List<CategoryEntity>>()

    fun getCategory(): LiveData<List<CategoryEntity>> {

       viewModelJob = GlobalScope.launch(dispatchers.main, CoroutineStart.DEFAULT) {

            try {

                val categories = withContext(dispatchers.network) {
                    categoryRepository.loadCategory()
                }
                result.postValue(categories.await())

            } catch (exception: Exception) {
                Log.e(CategoryViewModel::class.java.simpleName, exception.message)
                applicationController.sendErrorChannel(exception.message.plus("Exception: getCategory"))
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