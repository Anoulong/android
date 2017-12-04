package com.quickseries.ra;

import android.app.Application;
import android.content.Context;

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
public class ResourceApplication extends Application{
    private static final String TAG = ResourceApplication.class.getSimpleName();

    private ApplicationComponent mApplicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        ResourceApplication.getApplicationComponent(this).inject(this);

    }

    protected ApplicationModule getApplicationModule() {
        return new ApplicationModule(this);
    }

    public static ApplicationComponent getApplicationComponent(Context context) {
        ResourceApplication application = (ResourceApplication) context.getApplicationContext();
        if (application.mApplicationComponent == null) {
            application.mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(application.getApplicationModule())
                    .build();
        }
        return application.mApplicationComponent;
    }
}
