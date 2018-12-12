package com.anou.prototype.yoga.base

import android.content.Context
import android.os.Bundle
import android.view.View

import com.anou.prototype.yoga.navigation.MainNavigationListener


/*******************************************************************************
 * QuickSeries® Publishing inc.
 *
 *
 * Copyright (c) 1992-2018 QuickSeries® Publishing inc.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information of QuickSeries®
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with QuickSeries® and QuickSeries's Partners.
 *
 *
 * Created by Leila Faraghi on 2018-05-16.
 */
abstract class BaseMainFragment : BaseFragment() {

    protected var mainNavigationListener: MainNavigationListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mainNavigationListener == null) {
            mainNavigationListener = activity as MainNavigationListener?
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context?.let {
            try {
                mainNavigationListener = context as MainNavigationListener?
            } catch (e: ClassCastException) {
                throw ClassCastException(context.toString() + " must implement MainNavigationListener")
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        mainNavigationListener = null
    }

    companion object {

        private val TAG = BaseMainFragment::class.java.simpleName
    }
}
