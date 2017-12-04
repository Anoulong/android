package com.quickseries.rca.repository;

import android.arch.lifecycle.LiveData;

import com.quickseries.rca.local.ModuleEntity;
import com.quickseries.rca.local.ModuleDao;
import com.quickseries.rca.remote.ApiService;

import java.util.concurrent.Executor;

public class RcaRepository {

    private final ApiService apiService;
    private final ModuleDao moduleDao;
    private final Executor executor;

    public RcaRepository(ApiService apiService, ModuleDao moduleDao, Executor executor) {
        this.apiService = apiService;
        this.moduleDao = moduleDao;
        this.executor = executor;
    }

    public LiveData<ModuleEntity> getModules(String appId) {
        fetchModules(appId);
        // return a LiveData directly from the database.
        return moduleDao.loadModules();
    }

    private void fetchModules(String appId) {
        executor.execute(() -> {
//            // running in a background thread
//            // check if module was fetched recently
//            boolean userExists = moduleDao.hasUser(FRESH_TIMEOUT);
//            if (!userExists) {
//                // refresh the data
//                Response response = apiService.getModules(appId).execute();
//                // TODO check for error etc.
//                // Update the database.The LiveData will automatically refresh so
//                // we don't need to do anything else here besides updating the database
//                moduleDao.save(response.body());
//            }
        });
    }
}
