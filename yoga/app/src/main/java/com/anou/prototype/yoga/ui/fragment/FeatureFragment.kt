package com.anou.prototype.yoga.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseMainFragment
import com.anou.prototype.yoga.common.Constants
import com.anou.prototype.yoga.databinding.FragmentCategoryBinding
import com.anou.prototype.yoga.db.category.CategoryAdapter
import com.anou.prototype.yoga.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception


class FeatureFragment : BaseMainFragment() {
    val categoryViewModel by viewModel<CategoryViewModel>()
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
                mainNavigationListener?.onFragmentViewed(title.toString())
            }
        }

        // Bind views
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        adapter = CategoryAdapter(this, inflater)
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
