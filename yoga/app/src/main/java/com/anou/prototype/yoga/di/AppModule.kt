package com.anou.prototype.yoga.di

import androidx.room.Room
import com.anou.prototype.yoga.BuildConfig
import com.anou.prototype.core.api.ApiService
import com.anou.prototype.core.common.AppCoroutineDispatchers
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.yoga.controller.ApplicationControllerImpl
import com.anou.prototype.core.db.ApplicationDatabase
import com.anou.prototype.core.repository.CategoryRepository
import com.anou.prototype.core.repository.FeatureRepository
import com.anou.prototype.core.repository.ModuleRepository
import com.anou.prototype.core.viewmodel.CategoryViewModel
import com.anou.prototype.core.viewmodel.FeatureViewModel
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.navigation.MainRouter
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.quickseries.core.service.NetworkConnectivityService
import com.quickseries.service.network.NetworkConnectivityServiceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val commonModule = module {
    single { Room.databaseBuilder(androidApplication(), ApplicationDatabase::class.java, ApplicationDatabase.DATABASE_NAME).build() }
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

    single { AppCoroutineDispatchers() }
    single { NetworkConnectivityServiceImpl() as NetworkConnectivityService }
    single { ApplicationControllerImpl() as ApplicationController }
    single { MainRouter() }
}

val repositoryModule = module {
    single { ModuleRepository(get(), get(), get()) }
    single { CategoryRepository(get(), get(), get()) }
    single { FeatureRepository(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { CategoryViewModel(get(), get(), get()) }
    viewModel { FeatureViewModel(get(), get(), get()) }
}

val modules = listOf(commonModule, repositoryModule, viewModelModule)