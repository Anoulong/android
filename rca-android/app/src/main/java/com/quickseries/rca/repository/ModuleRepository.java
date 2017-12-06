package com.quickseries.rca.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quickseries.rca.common.ApiResponse;
import com.quickseries.rca.common.NetworkBoundResource;
import com.quickseries.rca.common.Resource;
import com.quickseries.rca.controller.ExecutorController;
import com.quickseries.rca.local.ApplicationDatabase;
import com.quickseries.rca.local.ModuleEntity;
import com.quickseries.rca.remote.ApiService;
import com.quickseries.rca.service.NetworkConnectivityService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

/**
 * Repository that handles Module objects.
 */
@Singleton
public class ModuleRepository {

    private static final String TAG = ModuleRepository.class.getSimpleName();

    private ApiService apiService;
    private ApplicationDatabase applicationDatabase;
    private final ExecutorController  executorController;

    @Inject
    public ModuleRepository(ExecutorController executorController, ApplicationDatabase applicationDatabase, ApiService apiService) {
        this.apiService = apiService;
        this.applicationDatabase = applicationDatabase;
        this.executorController = executorController;
    }

    public LiveData<Resource<List<ModuleEntity>>> loadModules(String authorizationToken, String appId) {

        return new NetworkBoundResource<List<ModuleEntity>,List<ModuleEntity>>(executorController) {

            @Override
            protected void saveCallResult(@NonNull List<ModuleEntity> item) {
                applicationDatabase.moduleDao().insertAll(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ModuleEntity> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<ModuleEntity>> loadFromDb() {
                return applicationDatabase.moduleDao().loadAllModules();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<ModuleEntity>>> createCall() {
                return apiService.fetchModules(authorizationToken, appId);
            }

        }.asLiveData();


//        networkConnectivityService.getConnectionTypeObservable()
//                .subscribe(connectionType -> {
//                    if (!connectionType.equals(NetworkConnectivityService.ConnectionType.TYPE_NO_INTERNET)) {
//                        refreshModules(authorizationToken, appId);
//                    }else{
//
//                    }
//                });
//
//        // return a LiveData directly from the database.
//        return this.applicationDatabase.moduleDao().loadAllModules();
    }

//    private void refreshModules(String authorizationToken, String appId) {
//        executor.execute(() -> {
//            Response<List<ModuleEntity>> response = null;
//            // refresh the data
//            try {
//                response = apiService.fetchModules(authorizationToken, appId).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            // Update the database.The LiveData will automatically refresh so
//            // we don't need to do anything else here besides updating the database
//            if (response != null) {
//                applicationDatabase.moduleDao().insertAll(response.body());
//            }
//
//        });
//    }
}
