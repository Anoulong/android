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

import com.anou.prototype.yoga.db.ModuleEntity
import com.anou.prototype.yoga.db.news.NewsEntity
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET

/**
 * REST API access points
 */
interface ApiService {
    companion object {
        val URL = "https://raw.githubusercontent.com/Anoulong/android/master/api-example/"
    }

    @GET("modules.json")
    fun fetchModules(): Deferred<List<ModuleEntity>>

    @GET("news.json")
    fun fetchNews(): Deferred<List<NewsEntity>>
}
