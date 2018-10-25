/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anou.prototype.yoga.api

import androidx.lifecycle.LiveData
import com.anou.prototype.yoga.db.ModuleEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * REST API access points
 */
interface ApiService {
    companion object {
        val URL = "https://api-dev.quickseries.com/v3/"
    }

    @GET("apps/{appId}/custom-modules")
    abstract fun fetchModules(@Header("Authorization") authorizationToken: String, @Path("appId") appId: String): LiveData<ApiResponse<List<ModuleEntity>>>
}
