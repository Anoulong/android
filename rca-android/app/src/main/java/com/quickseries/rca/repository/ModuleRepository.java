package com.quickseries.rca.repository;

import android.arch.lifecycle.LiveData;

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
    private Executor executor;
    private NetworkConnectivityService networkConnectivityService;

    @Inject
    public ModuleRepository(ApplicationDatabase applicationDatabase, ApiService apiService, Executor executor, NetworkConnectivityService networkConnectivityService) {
        this.apiService = apiService;
        this.applicationDatabase = applicationDatabase;
        this.executor = executor;
        this.networkConnectivityService = networkConnectivityService;
    }

    public LiveData<List<ModuleEntity>> getModules(String authorizationToken, String appId) {
        networkConnectivityService.getConnectionTypeObservable()
                .subscribe(connectionType -> {
                    if (!connectionType.equals(NetworkConnectivityService.ConnectionType.TYPE_NO_INTERNET)) {
                        refreshModules(authorizationToken, appId);
                    }else{

                    }
                });

        // return a LiveData directly from the database.
        return this.applicationDatabase.moduleDao().loadAllModules();
    }

    private void refreshModules(String authorizationToken, String appId) {
        executor.execute(() -> {
            Response<List<ModuleEntity>> response = null;
            // refresh the data
            try {
                response = apiService.fetchModules(authorizationToken, appId).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Update the database.The LiveData will automatically refresh so
            // we don't need to do anything else here besides updating the database
            if (response != null) {
                applicationDatabase.moduleDao().insertAll(response.body());
            }

        });
    }
}
