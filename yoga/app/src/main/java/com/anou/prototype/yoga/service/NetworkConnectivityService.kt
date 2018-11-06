package com.anou.prototype.yoga.service

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.launch

/*******************************************************************************
 * QuickSeries® Publishing inc.
 *
 *
 * Copyright (c) 1992-2017 QuickSeries® Publishing inc.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information of QuickSeries®
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with QuickSeries® and QuickSeries's Partners.
 *
 *
 * Created by Anou Chanthavong on 2018-01-29.
 */

interface NetworkConnectivityService {

    suspend fun setConnectionType(connectionType: ConnectionType)
    fun receiveConnectionTypeChannel(): Channel<ConnectionType>

    enum class ConnectionType {
        TYPE_WIFI,
        TYPE_MOBILE,
        TYPE_NO_INTERNET
    }
}

class NetworkConnectivityServiceImpl : NetworkConnectivityService {

    private val connectionTypeChannel = Channel<NetworkConnectivityService.ConnectionType>()

    override suspend fun setConnectionType(connectionType: NetworkConnectivityService.ConnectionType) {
        connectionTypeChannel.send(connectionType)
    }

    override fun receiveConnectionTypeChannel(): Channel<NetworkConnectivityService.ConnectionType> {
        return connectionTypeChannel
    }

}