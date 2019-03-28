package com.anou.prototype.yoga

import org.koin.android.ext.android.startKoin
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.anou.prototype.yoga.di.modules
import com.facebook.stetho.Stetho

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
 * Created by Anou Chanthavong on 2018-10-20.
 ******************************************************************************/
class YogaApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin(this, modules)
        // Stetho
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this) // install multidex
    }

}
