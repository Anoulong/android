package com.anou.prototype.yoga.di

import androidx.room.Room
import com.android.example.github.api.ApiService
import com.android.example.github.db.ApplicationDatabase
import com.anou.prototype.yoga.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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
 * Created by Anou Chanthavong on 2018-10-24.
 ******************************************************************************/
val appModule = module {
    single {
        Room.databaseBuilder(androidApplication(), ApplicationDatabase::class.java, ApplicationDatabase.DATABASE_NAME)
            .build();
    }
    single {
        var client = OkHttpClient.Builder().build()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client = OkHttpClient.Builder().addInterceptor(interceptor).addNetworkInterceptor(StethoInterceptor())
                .build()
        }

        Retrofit.Builder()
            .baseUrl(ApiService.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}