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
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_faq.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FaqFragment : BaseMainFragment() {
    lateinit var moduleEid : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        arguments?.let {bundle ->
            bundle.get("moduleEid")?.let {eid ->
                moduleEid = eid.toString()
            }
        }
        return inflater.inflate(R.layout.fragment_faq   , container, false)
    }

    val mainViewModel by viewModel<MainViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val navBuilder = NavOptions.Builder()
//        val navOptions = navBuilder.setPopUpTo(R.id.loadingFragment, true).build()
//        NavHostFragment.findNavController(this).navigate(R.id.welcomeFragment, null, navOptions)


        textViewTitleFaq?.text = moduleEid
        mainNavigationListener?.onFragmentViewed("FAQ Fragment")
    }

}
