package com.quickseries.rca.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.quickseries.rca.local.ApplicationDatabase;
import com.quickseries.rca.local.ModuleEntity;
import com.quickseries.rca.local.ModuleDao;
import com.quickseries.rca.remote.ApiService;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RcaRepository {

    private ApiService apiService;
    private ApplicationDatabase applicationDatabase;

    public RcaRepository(ApplicationDatabase applicationDatabase, ApiService apiService) {
        this.apiService = apiService;
        this.applicationDatabase = applicationDatabase;
    }

    public LiveData<List<ModuleEntity>> getModules(String appId) {
        refreshModules(appId);
        // return a LiveData directly from the database.
        return this.applicationDatabase.moduleDao().loadAllModules();
    }

    private void refreshModules(String appId) {
        final MutableLiveData<List<ModuleEntity>> liveData = new MutableLiveData<>();
        Call<List<ModuleEntity>> call = apiService.getModules(appId);
        call.enqueue(new Callback<List<ModuleEntity>>() {
            @Override
            public void onResponse(Call<List<ModuleEntity>> call, Response<List<ModuleEntity>> response) {
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ModuleEntity>> call, Throwable t) {
//                liveData.setValue(t.getMessage());
            }

        });
        this.applicationDatabase.moduleDao().insertAll(liveData.getValue());
    }
}
