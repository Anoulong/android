package com.anou.prototype.yoga.controller

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel

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
 * Created by Anou Chanthavong on 2018-10-29.

 ******************************************************************************/
interface ApplicationController {
    fun receiveErrorChannel(): Channel<String>
    suspend fun sendErrorChannel(message: String)

}

class ApplicationControllerImpl() : ApplicationController {

    var errorChannel = Channel<String>()

    override fun receiveErrorChannel(): Channel<String> {
        return errorChannel
    }

    override suspend fun sendErrorChannel(message: String) {
        errorChannel.send(message)
    }

}