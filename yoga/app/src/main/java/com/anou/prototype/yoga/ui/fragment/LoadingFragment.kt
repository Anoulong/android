package com.anou.prototype.yoga.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.NavOptions
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.base.BaseMainFragment
import com.anou.prototype.yoga.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoadingFragment : BaseMainFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mainNavigationListener?.onFragmentViewed("Loading...")
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }
}
