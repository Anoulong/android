package com.anou.prototype.yoga.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.AboutActivity
import org.koin.android.ext.android.inject


class LoadingFragment : BaseFragment() {
    override val fragmentTag: String
        get() = LoadingFragment::class.java.simpleName
    val mainRouter: MainRouter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mainRouter.onFragmentViewed(activity as AboutActivity, "Loading...")
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }
}
