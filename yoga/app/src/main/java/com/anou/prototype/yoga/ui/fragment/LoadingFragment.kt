package com.anou.prototype.yoga.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseMainFragment
import com.anou.prototype.core.viewmodel.MainViewModel


class LoadingFragment : BaseMainFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mainNavigationListener?.onFragmentViewed("Loading...")
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }
}
