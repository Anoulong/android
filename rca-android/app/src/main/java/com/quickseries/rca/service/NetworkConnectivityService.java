package com.quickseries.rca.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

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
 * Created by Anou Chanthavong on 2017-12-05.
 ******************************************************************************/
public class NetworkConnectivityService {

    private static final String TAG = NetworkConnectivityService.class.getSimpleName();

    public enum ConnectionType {
        TYPE_WIFI,
        TYPE_MOBILE,
        TYPE_NO_INTERNET
    }

    private BehaviorSubject<ConnectionType> connectionTypeObservable =  BehaviorSubject.createDefault(ConnectionType.TYPE_NO_INTERNET);

    public NetworkConnectivityService() {
    }

    public ConnectionType getConnectionType() {
        return connectionTypeObservable.blockingLast();
    }

    public Observable<ConnectionType> getConnectionTypeObservable() {
        return connectionTypeObservable;
    }

    public void setConnectionType(ConnectionType newConnectionType) {
        if (newConnectionType != getConnectionType()) {
            Log.v(TAG, String.format("Network connection status: %s", newConnectionType.name()));
        }
        connectionTypeObservable.onNext(newConnectionType);
    }
}
