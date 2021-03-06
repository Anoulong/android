package com.quickseries.rca.di;

import com.quickseries.rca.RcaApplication;
import com.quickseries.rca.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

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
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    // Injectable Application
    void inject(RcaApplication target);

    // Injectable Activities
    void inject(MainActivity target);

}
