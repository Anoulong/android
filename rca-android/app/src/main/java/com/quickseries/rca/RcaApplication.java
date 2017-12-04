package com.quickseries.rca;

import android.app.Application;
import android.content.Context;

import com.quickseries.rca.di.ApplicationComponent;
import com.quickseries.rca.di.ApplicationModule;
import com.quickseries.rca.di.DaggerApplicationComponent;

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
 * Created by Anou Chanthavong on 2017-12-04.
 ******************************************************************************/
public class RcaApplication extends Application {
    private static final String TAG = RcaApplication.class.getSimpleName();

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        RcaApplication.getApplicationComponent(this).inject(this);
    }

    protected ApplicationModule getApplicationModule() {
        return new ApplicationModule(this);
    }

    public static ApplicationComponent getApplicationComponent(Context context) {
        RcaApplication application = (RcaApplication) context.getApplicationContext();
        if (application.applicationComponent == null) {
            application.applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(application.getApplicationModule())
                    .build();
        }
        return application.applicationComponent;
    }}
