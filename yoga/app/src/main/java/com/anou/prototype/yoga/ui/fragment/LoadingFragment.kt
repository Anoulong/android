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


class LoadingFragment : BaseMainFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mainNavigationListener?.onFragmentViewed("I am in Loading Fragment!")
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navBuilder = NavOptions.Builder()
        val navOptions = navBuilder.setPopUpTo(R.id.loadingFragment, true).build()
//        NavHostFragment.findNavController(this).navigate(R.id.welcomeFragment, null, navOptions)
    }

    override fun onDetach() {
        super.onDetach()
        mainNavigationListener = null
    }
}
