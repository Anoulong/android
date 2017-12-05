package com.quickseries.rca.remote;

import com.quickseries.rca.local.ModuleEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

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
public interface ApiService {
    String URL = "https://api-dev.quickseries.com/v3/";

    @GET("apps/{appId}/custom-modules")
    Call<List<ModuleEntity>> fetchModules(@Header("Authorization") String authorizationToken, @Path("appId") String appId);
}
