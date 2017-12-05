package com.quickseries.rca.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.quickseries.rca.local.ApplicationDatabase;
import com.quickseries.rca.local.ModuleEntity;
import com.quickseries.rca.remote.ApiService;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RcaRepository {

    private static final String TAG = RcaRepository.class.getSimpleName();

    private ApiService apiService;
    private ApplicationDatabase applicationDatabase;
    private Executor executor;

    public RcaRepository(ApplicationDatabase applicationDatabase, ApiService apiService, Executor executor) {
        this.apiService = apiService;
        this.applicationDatabase = applicationDatabase;
        this.executor = executor;
    }

    public LiveData<List<ModuleEntity>> getModules(String authorizationToken, String appId) {
        refreshModules(authorizationToken, appId);
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
                applicationDatabase.moduleDao().insertAll(response.body());

        });
    }
}
