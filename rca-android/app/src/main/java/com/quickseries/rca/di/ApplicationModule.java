package com.quickseries.rca.di;

import com.quickseries.rca.RcaApplication;

import dagger.Module;

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

    protected final RcaApplication application;

    public ApplicationModule(RcaApplication application) {
        this.application = application;
    }
}