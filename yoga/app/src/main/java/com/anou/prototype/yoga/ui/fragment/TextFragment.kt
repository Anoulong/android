package com.anou.prototype.yoga.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.utils.Constants
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_text.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class TextFragment : BaseFragment() {
    val mainViewModel by viewModel<MainViewModel>()
    val mainRouter: MainRouter by inject()

    lateinit var moduleEid : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        arguments?.let {bundle ->
            bundle.get(Constants.MODULE_EID)?.let { eid ->
                moduleEid = eid.toString()
            }
        }
        return inflater.inflate(R.layout.fragment_text   , container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val navBuilder = NavOptions.Builder()
//        val navOptions = navBuilder.setPopUpTo(R.id.loadingFragment, true).build()
//        NavHostFragment.findNavController(this).navigate(R.id.welcomeFragment, null, navOptions)


        textViewTitleText ?.text = moduleEid
        mainRouter.onFragmentViewed(activity as MainActivity, "Text Fragment")
    }

}
