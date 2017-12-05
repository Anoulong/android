package com.quickseries.rca.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.quickseries.rca.local.ModuleEntity;
import com.quickseries.rca.repository.RcaRepository;

import java.util.List;

import javax.inject.Inject;

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
public class ModuleListViewModel extends ViewModel {
//    private final RcaRepository rcaRepository;

    public ModuleListViewModel() {
//        this.rcaRepository = rcaRepository;
    }

    /**
     * Expose the LiveData Modules query so the UI can observe it.
     */
//    public LiveData<List<ModuleEntity>> getModules(String appId) {
//        return rcaRepository.getModules(appId);
//    }
}
