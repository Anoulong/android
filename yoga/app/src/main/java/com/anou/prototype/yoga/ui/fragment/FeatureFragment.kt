package com.anou.prototype.yoga.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.utils.Constants
import com.anou.prototype.yoga.databinding.FragmentFeatureBinding
import com.anou.prototype.core.viewmodel.FeatureViewModel
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.MainActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class FeatureFragment : BaseFragment() {
    val featureViewModel by viewModel<FeatureViewModel>()
    val mainRouter: MainRouter by inject()

    lateinit var binding: FragmentFeatureBinding
    lateinit var adapter: FeatureAdapter
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feature, container, false)
        adapter = FeatureAdapter(this, inflater, mainRouter)
        binding.setLifecycleOwner(this)
        binding.featureRecyclerView.adapter = adapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            featureViewModel.getFeatures().observe(this, Observer { features ->

                features?.let { listOfFeature ->
                    adapter.setData(listOfFeature)
                }

            })
        } catch (e: Exception) {
            Log.e("FeatureFragment", e.message)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.featureViewModel = featureViewModel
    }

}
