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
import com.anou.prototype.yoga.databinding.FragmentCategoryBinding
import com.anou.prototype.core.viewmodel.CategoryViewModel
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.MainActivity
import com.squareup.haha.perflib.Main
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception


class CategoryFragment : BaseFragment() {
    val categoryViewModel by viewModel<CategoryViewModel>()
    val mainRouter: MainRouter by inject()

    lateinit var binding: FragmentCategoryBinding
    lateinit var adapter: CategoryAdapter
    lateinit var moduleEid: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        arguments?.let { bundle ->
            bundle.get(Constants.MODULE_EID)?.let { eid ->
                moduleEid = eid.toString()
            }
            bundle.get(Constants.MODULE_TITLE)?.let { title ->
                mainRouter?.onFragmentViewed(activity as MainActivity, title.toString())
            }
        }

        // Bind views
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        adapter = CategoryAdapter(this, inflater, mainRouter)
        binding.setLifecycleOwner(this)
        binding.categoryRecyclerView.adapter = adapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            categoryViewModel.getCategory().observe(this, Observer { categories ->

                categories?.let { listOfCategory ->
                    adapter.setData(listOfCategory)
//                    textViewTitleFaq?.text =  "Count = ${listOfFaqs.count()}"
                }

            })
        } catch (e: Exception) {
            Log.e("CategoryFragment", e.message)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.categoryViewModel = categoryViewModel
    }

}
