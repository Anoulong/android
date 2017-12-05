package com.quickseries.rca.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.quickseries.rca.local.ApplicationDatabase;
import com.quickseries.rca.local.ModuleEntity;
import com.quickseries.rca.remote.ApiService;
import com.quickseries.rca.service.NetworkConnectivityService;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RcaRepository {

    private static final String TAG = RcaRepository.class.getSimpleName();

    private ApiService apiService;
    private ApplicationDatabase applicationDatabase;
    private Executor executor;
    private NetworkConnectivityService networkConnectivityService;

    public RcaRepository(ApplicationDatabase applicationDatabase, ApiService apiService, Executor executor, NetworkConnectivityService networkConnectivityService) {
        this.apiService = apiService;
        this.applicationDatabase = applicationDatabase;
        this.executor = executor;
        this.networkConnectivityService = networkConnectivityService;
    }

    public LiveData<List<ModuleEntity>> getModules(String authorizationToken, String appId) {
        networkConnectivityService.getConnectionTypeObservable()
                .filter(c -> !c.equals(NetworkConnectivityService.ConnectionType.TYPE_NO_INTERNET))
                .subscribe(connectionType -> {
                    refreshModules(authorizationToken, appId);
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
