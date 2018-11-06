package com.anou.prototype.yoga.di

import androidx.room.Room
import com.anou.prototype.yoga.BuildConfig
import com.anou.prototype.yoga.api.ApiService
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.controller.ApplicationController
import com.anou.prototype.yoga.controller.ApplicationControllerImpl
import com.anou.prototype.yoga.db.ApplicationDatabase
import com.anou.prototype.yoga.repository.ModuleRepository
import com.anou.prototype.yoga.service.NetworkConnectivityService
import com.anou.prototype.yoga.service.NetworkConnectivityServiceImpl
import com.anou.prototype.yoga.service.NetworkStateBroadcastReceiver
import com.anou.prototype.yoga.viewmodel.MainViewModel
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.newSingleThreadContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.experimental.CoroutineContext

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
val commonModule = module {
    single {
        Room.databaseBuilder(androidApplication(), ApplicationDatabase::class.java, ApplicationDatabase.DATABASE_NAME)
            .build()
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
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    single {
        AppCoroutineDispatchers()
    }

    single {
        ApplicationControllerImpl() as ApplicationController
    }

    single {
        NetworkConnectivityServiceImpl() as NetworkConnectivityService
    }

    single {
        NetworkStateBroadcastReceiver(get())
    }
}

val repositoryModule = module {
//    single { HelloServiceImpl(get()) as HelloService }
    single { ModuleRepository(get(), get(), get()) }

    // Declare a controller
//    controller { HelloController(get()) }
}

val viewModelModule = module {
    //    single { HelloServiceImpl(get()) as HelloService }
    viewModel { MainViewModel(get(), get(), get()) }

    // Declare a controller
//    controller { HelloController(get()) }
}

val modules = listOf(commonModule, repositoryModule, viewModelModule)