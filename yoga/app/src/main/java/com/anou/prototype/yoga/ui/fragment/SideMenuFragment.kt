package com.anou.prototype.yoga.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.utils.Constants
import com.anou.prototype.yoga.databinding.FragmentSideMenuBinding
import com.anou.prototype.core.viewmodel.FeatureViewModel
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.MainActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SideMenuFragment : BaseFragment() {
    val mainViewModel by viewModel<MainViewModel>()
    val mainRouter: MainRouter by inject()

    lateinit var binding: FragmentSideMenuBinding
    lateinit var adapter: SideMenuAdapter
    lateinit var categoryEid: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        arguments?.let { bundle ->
            bundle.get(Constants.CATEGORY_EID)?.let { eid ->
                categoryEid = eid.toString()
            }
            bundle.get(Constants.CATEGORY_TITLE)?.let { title ->
                mainRouter.onFragmentViewed(activity as MainActivity, title.toString())
            }
        }

        // Bind views
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_side_menu, container, false)
        adapter = SideMenuAdapter(this, inflater = LayoutInflater.from(activity), mainRouter = mainRouter)
        binding.setLifecycleOwner(this)
        binding.sideMenuRecyclerView.adapter = adapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            mainViewModel.getModules().observe(this, Observer { modules ->
                adapter.setData(modules)

                //initialize the first module as the landing screen
                val firstModule = modules.get(0)
                mainRouter.onModuleSelected(activity as MainActivity, firstModule, true)


            })
        } catch (e: Exception) {
            Log.e("FeatureFragment", e.message)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.mainViewModel = mainViewModel
    }

}
