package com.quickseries.rca.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import static android.telephony.TelephonyManager.DATA_CONNECTED;

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
public class NetworkStateBroadcastReceiver  extends BroadcastReceiver {

    private static final String TAG = NetworkStateBroadcastReceiver.class.getSimpleName();

    private NetworkConnectivityService networkConnectivityService;

    private NetworkInfo networkInfo;
    private TelephonyStateListener telephonyListener;

    public NetworkStateBroadcastReceiver() {
    }

    public NetworkStateBroadcastReceiver(NetworkConnectivityService networkReachabilityService) {
        this.networkConnectivityService = networkReachabilityService;
        this.telephonyListener = new TelephonyStateListener();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (networkConnectivityService != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            networkInfo = cm.getActiveNetworkInfo();

            boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

            if (isConnected) {
                switch (networkInfo.getType()) {
                    case ConnectivityManager.TYPE_MOBILE:
                        tm.listen(telephonyListener, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
                        networkConnectivityService.setConnectionType(NetworkConnectivityService.ConnectionType.TYPE_MOBILE);
                        break;
                    case ConnectivityManager.TYPE_WIFI:
                        tm.listen(telephonyListener, PhoneStateListener.LISTEN_NONE);
                        networkConnectivityService.setConnectionType(NetworkConnectivityService.ConnectionType.TYPE_WIFI);
                        break;
                }
            } else {
                tm.listen(telephonyListener, PhoneStateListener.LISTEN_NONE);
                networkConnectivityService.setConnectionType(NetworkConnectivityService.ConnectionType.TYPE_NO_INTERNET);
            }
        }
    }

    /**
     * Handle case Mobile connection type to trigger changed
     */
    public class TelephonyStateListener extends PhoneStateListener {
        @Override
        public void onDataConnectionStateChanged(int state, int networkType) {
            switch (state) {
                case DATA_CONNECTED:
                    networkConnectivityService.setConnectionType(NetworkConnectivityService.ConnectionType.TYPE_MOBILE);
                    break;
                default:
                    networkConnectivityService.setConnectionType(NetworkConnectivityService.ConnectionType.TYPE_NO_INTERNET);
                    break;
            }
        }
    }
}
