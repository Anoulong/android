package com.anou.prototype.yoga.common

import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.newSingleThreadContext
import kotlinx.coroutines.experimental.android.UI


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
 * Created by Anou Chanthavong on 2018-10-25.
 ******************************************************************************/
data class AppCoroutineDispatchers(
    val database: CoroutineDispatcher = newSingleThreadContext("database"),
    val computation: CoroutineDispatcher = newSingleThreadContext("computation"),
    val network: CoroutineDispatcher = newSingleThreadContext("network"),
    val main: CoroutineDispatcher = UI
)