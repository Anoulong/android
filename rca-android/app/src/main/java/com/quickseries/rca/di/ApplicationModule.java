package com.quickseries.rca.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.quickseries.rca.RcaApplication;
import com.quickseries.rca.local.ApplicationDatabase;
import com.quickseries.rca.remote.ApiService;
import com.quickseries.rca.repository.RcaRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*******************************************************************************
 * QuickSeries® Publishing inc.
 * <p>
 * Copyright (c) 1992-2017 QuickSeries® Publishing inc.
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of QuickSeries®
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with QuickSeries® and QuickSeries's Partners.
 * <p>
 * Created by Anou Chanthavong on 2017-12-01.
 ******************************************************************************/
@Module
public class ApplicationModule {

    private static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

    private RcaApplication  application;

     public ApplicationModule(RcaApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application provideRcaApplication() {
        return application;
    }

    @Singleton
    @Provides
    ApplicationDatabase provideApplicationDatabase() {
        return Room.databaseBuilder(application, ApplicationDatabase.class, ApplicationDatabase.DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    public ApiService providesApiService(){
        return new Retrofit.Builder()
                .baseUrl(ApiService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @Provides
    @Singleton
    public RcaRepository providesRcaRepository(ApplicationDatabase applicationDatabase, ApiService apiService){
        return new RcaRepository(applicationDatabase, apiService);
    }
}